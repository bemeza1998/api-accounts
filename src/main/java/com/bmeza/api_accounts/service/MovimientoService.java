package com.bmeza.api_accounts.service;

import java.util.Optional;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bmeza.api_accounts.config.BaseURL;
import com.bmeza.api_accounts.dao.CuentaRepository;
import com.bmeza.api_accounts.dao.MovimientoRepository;
import com.bmeza.api_accounts.dto.MovimientoEstadoCuentaDTO;
import com.bmeza.api_accounts.enums.EstadoCuentaEnum;
import com.bmeza.api_accounts.model.Cuenta;
import com.bmeza.api_accounts.model.Movimiento;
import com.bmeza.api_accounts.exception.CustomerNotActiveException;
import com.bmeza.api_accounts.exception.InactiveAccountException;
import com.bmeza.api_accounts.exception.InsufficientBalanceException;
import com.bmeza.api_accounts.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final BaseURL baseURL;
    private final WebClient webClient;

    public void crearMovimiento(Movimiento movimiento) {

        this.movimientoRepository.save(movimiento);

    }

    public Movimiento buscarMovimiento(Long id) {
        Optional<Movimiento> movimientoOpt = this.movimientoRepository.findById(id);
        return movimientoOpt.orElseThrow(
                () -> new NotFoundException("No se encontró el movimiento " + id));
    }

    public void modificarMovimiento(Movimiento movimiento) {
        Optional<Movimiento> movimientoOpt = this.movimientoRepository.findById(movimiento.getId());
        if (movimientoOpt.isPresent()) {
            Movimiento movimientoBd = movimientoOpt.get();
            movimientoBd.setFecha(movimiento.getFecha());
            movimientoBd.setTipo(movimiento.getTipo());
            movimientoBd.setValor(movimiento.getValor());
            movimientoBd.setSaldoPostMovimiento(movimiento.getSaldoPostMovimiento());

            this.movimientoRepository.save(movimientoBd);
        } else {
            throw new NotFoundException("No se encontró el movimiento " + movimiento.getId());
        }

    }

    public void eliminarMovimiento(Long id) {
        Optional<Movimiento> movimientoOpt = this.movimientoRepository.findById(id);
        if (movimientoOpt.isPresent()) {
            this.movimientoRepository.delete(movimientoOpt.get());
        } else {
            throw new NotFoundException("No se encontró el movimiento " + id);
        }
    }

    public Movimiento realizarMovimiento(Movimiento movimiento) {
        Optional<Cuenta> cuentaOpt = this.cuentaRepository.findByNumeroCuenta(movimiento.getNumeroCuenta());

        if (cuentaOpt.isPresent()) {
            Cuenta cuentaDb = cuentaOpt.get();
            try {
                webClient.get()
                        .uri(baseURL.getApiClientsURL() + "/api/clientes/activo/" + cuentaDb.getIdCliente()).retrieve()
                        .bodyToMono(Boolean.class).block();
            } catch (Exception e) {
                throw new CustomerNotActiveException("El cliente no puede realizar transacciones");
            }
            if (!cuentaDb.getEstado().equals(EstadoCuentaEnum.ACTIVA.getValor())) {
                throw new InactiveAccountException("La cuenta no se encuentra activa");
            }
            if (cuentaDb.getSaldo().add(movimiento.getValor()).signum() >= 0) {
                cuentaDb.setSaldo(cuentaDb.getSaldo().add(movimiento.getValor()));
                movimiento.setSaldoPostMovimiento(cuentaDb.getSaldo());
                this.cuentaRepository.save(cuentaDb);
                return this.movimientoRepository.save(movimiento);
            } else {
                throw new InsufficientBalanceException("Saldo no disponible");
            }
        } else {
            throw new NotFoundException("No se encontró la cuenta número " + movimiento.getNumeroCuenta());
        }
    }

    public List<MovimientoEstadoCuentaDTO> generarReporte(String idCliente, String fechaInicial, String fechaFinal)
            throws ParseException {

        SimpleDateFormat fechaEntrada = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaFormatoInicial = fechaEntrada.parse(fechaInicial);
        Date fechaFormatoFinal = fechaEntrada.parse(fechaFinal);

        List<Object[]> results = movimientoRepository.findMovimientosByCliente(idCliente, fechaFormatoInicial,
                fechaFormatoFinal);

        List<MovimientoEstadoCuentaDTO> movimientos = new ArrayList<>();
        for (Object[] result : results) {
            MovimientoEstadoCuentaDTO movimientoDTO = MovimientoEstadoCuentaDTO.builder()
                    .nombreCliente((String) result[0])
                    .identificacionCliente((String) result[1])
                    .numeroCuenta((String) result[2])
                    .tipoCuenta((String) result[3])
                    .saldoInicial((BigDecimal) result[4])
                    .fechaMovimiento((Date) result[5])
                    .saldoPostMovimiento((BigDecimal) result[6])
                    .valorMovimiento((BigDecimal) result[7])
                    .tipoMovimiento((String) result[8])
                    .build();

            movimientos.add(movimientoDTO);
        }

        return movimientos;
    }

}
