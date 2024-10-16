package com.bmeza.api_accounts.resource;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bmeza.api_accounts.dto.MovimientoDTO;
import com.bmeza.api_accounts.dto.MovimientoEstadoCuentaDTO;
import com.bmeza.api_accounts.mapper.MovimientoMapper;
import com.bmeza.api_accounts.model.Movimiento;
import com.bmeza.api_accounts.service.MovimientoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/movimientos")
public class MovimientoResource {

    private final MovimientoService service;

    @PostMapping
    public ResponseEntity<String> crearMovimiento(@RequestBody Movimiento Movimiento) {
        this.service.crearMovimiento(Movimiento);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Movimiento> buscarMovimiento(@RequestParam Long id) {
        Movimiento Movimiento = this.service.buscarMovimiento(id);
        return ResponseEntity.ok(Movimiento);
    }

    @PatchMapping
    public ResponseEntity<String> modificarMovimiento(@RequestBody Movimiento Movimiento) {
        this.service.modificarMovimiento(Movimiento);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> eliminarMovimiento(@RequestBody Long id) {
        this.service.eliminarMovimiento(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transaccion")
    public ResponseEntity<Movimiento> realizarMovimiento(@RequestBody MovimientoDTO dto) {
        Movimiento movimiento = MovimientoMapper.construirMovimiento(dto);
        return ResponseEntity.ok(this.service.realizarMovimiento(movimiento));
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<MovimientoEstadoCuentaDTO>> generarReporte(
            @RequestParam String idCliente,
            @RequestParam String fechaInical,
            @RequestParam String fechaFinal) {
                
        List<MovimientoEstadoCuentaDTO> movimientos;
        try {
            movimientos = this.service.generarReporte(idCliente, fechaInical, fechaFinal);
            return ResponseEntity.ok(movimientos);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
