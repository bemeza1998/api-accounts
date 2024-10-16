package com.bmeza.api_accounts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.bmeza.api_accounts.dao.CuentaRepository;
import com.bmeza.api_accounts.dao.MovimientoRepository;
import com.bmeza.api_accounts.exception.NotFoundException;
import com.bmeza.api_accounts.model.Cuenta;
import com.bmeza.api_accounts.model.Movimiento;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private MovimientoService movimientoService;

    private Movimiento movimiento;
    private RequestHeadersUriSpec requestHeadersUriSpec;
    private RequestHeadersSpec requestHeadersSpec;
    private ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        movimiento = Movimiento.builder()
                .id(1L)
                .numeroCuenta("85665")
                .fecha(new Date())
                .tipo("DEPOSITO")
                .valor(new BigDecimal(500))
                .saldoPostMovimiento(new BigDecimal(550))
                .build();

        requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
        requestHeadersSpec = mock(RequestHeadersSpec.class);
        responseSpec = mock(ResponseSpec.class);
    }

    @Test
    public void crearMovimiento_guardarMovimientoRepositorio() {

        movimientoService.crearMovimiento(movimiento);

        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    public void buscarMovimiento_movimientoExiste_retornarMovimiento() {

        when(movimientoRepository.findById(movimiento.getId())).thenReturn(Optional.of(movimiento));

        Movimiento movimientoEncontrado = movimientoService.buscarMovimiento(movimiento.getId());

        assertNotNull(movimientoEncontrado);
        verify(movimientoRepository, times(1)).findById(movimiento.getId());
    }

    @Test
    public void buscarMovimiento_movimientoNoExiste_retornarNotFoundException() {

        when(movimientoRepository.findById(movimiento.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> movimientoService.buscarMovimiento(movimiento.getId()));
    }

    @Test
    public void modificarMovimiento_movimientoExiste() {

        movimiento.setFecha(new Date());
        movimiento.setNumeroCuenta("55555");
        movimiento.setValor(new BigDecimal(550));
        movimiento.setSaldoPostMovimiento(new BigDecimal(600));

        when(movimientoRepository.findById(movimiento.getId())).thenReturn(Optional.of(movimiento));

        movimientoService.modificarMovimiento(movimiento);

        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    public void eliminarMovimiento_movimientoExiste() {

        when(movimientoRepository.findById(movimiento.getId())).thenReturn(Optional.of(movimiento));

        movimientoService.eliminarMovimiento(movimiento.getId());

        verify(movimientoRepository, times(1)).delete(movimiento);
    }

    @Test
    void realizarMovimiento_movimientoExistoso() {
        Cuenta cuenta = Cuenta.builder()
                .id(1L)
                .idCliente("CL-00003")
                .numeroCuenta("123")
                .tipo("AHORROS")
                .saldo(new BigDecimal("500"))
                .saldoInicial(new BigDecimal("500"))
                .estado("ACT")
                .build();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.just(true));

        when(cuentaRepository.findByNumeroCuenta(movimiento.getNumeroCuenta()))
                .thenReturn(Optional.of(cuenta));

        when(movimientoRepository.save(movimiento)).thenReturn(movimiento);

        Movimiento resultado = movimientoService.realizarMovimiento(movimiento);

        assertNotNull(resultado);
        assertEquals(new BigDecimal("1000"), cuenta.getSaldo());
    }

}
