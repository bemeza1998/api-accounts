package com.bmeza.api_accounts.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bmeza.api_accounts.model.Cuenta;
import com.bmeza.api_accounts.service.CuentaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CuentaResource.class)
public class CuentaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = Cuenta.builder()
                .idPersona("CL-00003")
                .numeroCuenta("123")
                .tipo("AHORROS")
                .saldo(new BigDecimal("500"))
                .saldoInicial(new BigDecimal("500"))
                .estado("ACT")
                .build();
    }

    @Test
    public void crearCuenta_cuentaCreada() throws Exception {

        doNothing().when(cuentaService).crearCuenta(any(Cuenta.class));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuenta)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void buscarCuenta_cuentaEncontrada() throws Exception {

        when(cuentaService.buscarCuenta(anyString())).thenReturn(cuenta);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas/" + cuenta.getNumeroCuenta()))
                .andReturn();

        Cuenta cuentaRespuesta = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cuenta.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(cuenta, cuentaRespuesta);
    }

    @Test
    public void modificarCuenta_cuentaModificada() throws Exception {

        doNothing().when(cuentaService).modificarCuenta(any(Cuenta.class));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuenta)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void eliminarCuenta_cuentaEliminada() throws Exception {

        doNothing().when(cuentaService).eliminarCuenta(anyString());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/cuentas/eliminar/" + cuenta.getNumeroCuenta()))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
