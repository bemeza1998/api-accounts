package com.bmeza.api_accounts.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bmeza.api_accounts.dto.MovimientoDTO;
import com.bmeza.api_accounts.dto.MovimientoEstadoCuentaDTO;
import com.bmeza.api_accounts.model.Movimiento;
import com.bmeza.api_accounts.service.MovimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MovimientoResource.class)
public class MovimientoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoService movimientoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Movimiento movimiento;
    private MovimientoDTO movimientoDTO;

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

        movimientoDTO = MovimientoDTO.builder()
                .numeroCuenta("85665")
                .valor(new BigDecimal("1000"))
                .build();
    }

    @Test
    public void crearMovimiento_movimientoCreado() throws Exception {

        doNothing().when(movimientoService).crearMovimiento(any(Movimiento.class));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimiento)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

    }

    @Test
    public void buscarMovimiento_movimientoEncontrado() throws Exception {
        when(movimientoService.buscarMovimiento(anyLong())).thenReturn(movimiento);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/movimientos")
                .param("id", "1"))
                .andReturn();

        Movimiento movimientoRespuesta = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                Movimiento.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(movimiento, movimientoRespuesta);
    }

    @Test
    public void modificarMovimiento_movimientoModifcado() throws Exception {
        doNothing().when(movimientoService).modificarMovimiento(any(Movimiento.class));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimiento)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void eliminarMovimiento_movimientoMEliminado() throws Exception {
        doNothing().when(movimientoService).eliminarMovimiento(anyLong());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(1L)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void realizarMovimiento_movimientoRealizado() throws Exception {
        when(movimientoService.realizarMovimiento(any(Movimiento.class))).thenReturn(movimiento);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/movimientos/transaccion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movimientoDTO)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void generarReporte_reporteGenerado() throws Exception {

        List<MovimientoEstadoCuentaDTO> reportes = List.of(new MovimientoEstadoCuentaDTO());

        String fechaInicial = "2024-10-12";
        String fechaFinal = "2024-10-16";

        when(movimientoService.generarReporte(anyString(), anyString(), anyString())).thenReturn(reportes);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/movimientos/reportes")
                .param("idCliente", "CL-00003")
                .param("fechaInicial", fechaInicial)
                .param("fechaFinal", fechaFinal))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}
