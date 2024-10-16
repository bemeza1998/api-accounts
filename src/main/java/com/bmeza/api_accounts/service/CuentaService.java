package com.bmeza.api_accounts.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bmeza.api_accounts.config.BaseURL;
import com.bmeza.api_accounts.dao.CuentaRepository;
import com.bmeza.api_accounts.exception.CustomerNotActiveException;
import com.bmeza.api_accounts.exception.ExistingAccountException;
import com.bmeza.api_accounts.exception.NotFoundException;
import com.bmeza.api_accounts.model.Cuenta;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository repository;
    private final BaseURL baseURL;
    private final WebClient webClient;

    public void crearCuenta(Cuenta cuenta) {
        Optional<Cuenta> cuentaExistente = this.repository.findByNumeroCuenta(cuenta.getNumeroCuenta());
        try {
            webClient.get()
                    .uri(baseURL.getApiClientsURL() + "/api/clientes/" + cuenta.getIdPersona()).retrieve()
                    .bodyToMono(Object.class).block();
        } catch (Exception e) {
            throw new CustomerNotActiveException("El cliente " + cuenta.getIdPersona() + " no existe");
        }

        if (!cuentaExistente.isPresent()) {
            cuenta.setSaldo(cuenta.getSaldoInicial());
            this.repository.save(cuenta);
        } else {
            throw new ExistingAccountException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe");
        }
    }

    public Cuenta buscarCuenta(String numeroCuenta) {
        Optional<Cuenta> cuentaOpt = this.repository.findByNumeroCuenta(numeroCuenta);
        return cuentaOpt.orElseThrow(
                () -> new NotFoundException("No se encontró la cuenta " + numeroCuenta));
    }

    public void modificarCuenta(Cuenta cuenta) {
        Optional<Cuenta> cuentaOpt = this.repository.findByNumeroCuenta(cuenta.getNumeroCuenta());
        if (cuentaOpt.isPresent()) {
            Cuenta cuentaBd = cuentaOpt.get();
            cuentaBd.setTipo(cuenta.getTipo());
            cuentaBd.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaBd.setSaldo(cuenta.getSaldo());
            cuentaBd.setEstado(cuenta.getEstado());

            this.repository.save(cuentaBd);
        } else {
            throw new NotFoundException("No se encontró la cuenta " + cuenta.getNumeroCuenta());
        }
    }

    public void eliminarCuenta(String numeroCuenta) {
        Optional<Cuenta> cuentaOpt = this.repository.findByNumeroCuenta(numeroCuenta);
        if (cuentaOpt.isPresent()) {
            this.repository.delete(cuentaOpt.get());
        } else {
            throw new NotFoundException("No se encontró la cuenta " + numeroCuenta);
        }
    }

}
