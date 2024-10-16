package com.bmeza.api_accounts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.bmeza.api_accounts.config.BaseURL;
import com.bmeza.api_accounts.dao.CuentaRepository;
import com.bmeza.api_accounts.model.Cuenta;

import reactor.core.publisher.Mono;

public class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private BaseURL baseURL;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private CuentaService cuentaService;

    private Cuenta cuenta;
    private RequestHeadersUriSpec requestHeadersUriSpec;
    private RequestHeadersSpec requestHeadersSpec;
    private ResponseSpec responseSpec;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = Cuenta.builder()
                .idPersona("CL-00003")
                .numeroCuenta("123")
                .tipo("AHORROS")
                .saldo(new BigDecimal("500"))
                .saldoInicial(new BigDecimal("500"))
                .estado("ACT")
                .build();

        requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
        requestHeadersSpec = mock(RequestHeadersSpec.class);
        responseSpec = mock(ResponseSpec.class);
    }

    @Test
    public void crearCuenta_guardarCuenta() {

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));

        when(cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()))
                .thenReturn(Optional.empty());

        cuentaService.crearCuenta(cuenta);

        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    public void buscarCuenta_cuentaEncontrada() {
        when(cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()))
                .thenReturn(Optional.of(cuenta));

        Cuenta result = cuentaService.buscarCuenta(cuenta.getNumeroCuenta());

        assertNotNull(result);
        assertEquals(cuenta.getNumeroCuenta(), result.getNumeroCuenta());
    }

    @Test
    public void modificar_cuentaModificada() {
        when(cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()))
                .thenReturn(Optional.of(cuenta));

        cuenta.setTipo("CORRIENTE");
        cuentaService.modificarCuenta(cuenta);

        verify(cuentaRepository, times(1)).save(cuenta);

    }

    @Test
    public void eliminarCuenta_cuentaEliminada() {

        when(cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta()))
                .thenReturn(Optional.of(cuenta));

        cuentaService.eliminarCuenta(cuenta.getNumeroCuenta());

        verify(cuentaRepository, times(1)).delete(cuenta);
    }
}
