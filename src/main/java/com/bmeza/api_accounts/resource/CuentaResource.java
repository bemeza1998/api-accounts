package com.bmeza.api_accounts.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bmeza.api_accounts.model.Cuenta;
import com.bmeza.api_accounts.service.CuentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/cuentas")
public class CuentaResource {

    private final CuentaService service;

    @PostMapping
    public ResponseEntity<String> crearCuenta(@RequestBody Cuenta Cuenta){
        this.service.crearCuenta(Cuenta);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> buscarCuenta(@PathVariable String numeroCuenta){
        Cuenta Cuenta = this.service.buscarCuenta(numeroCuenta);
        return ResponseEntity.ok(Cuenta);
    }

    @PutMapping
    public ResponseEntity<String> modificarCuenta(@RequestBody Cuenta Cuenta){
        this.service.modificarCuenta(Cuenta);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(("/eliminar/{numeroCuenta}"))
    public ResponseEntity<String> eliminarCuenta(@PathVariable String numeroCuenta){
        this.service.eliminarCuenta(numeroCuenta);
        return ResponseEntity.ok().build();
    }
    
}
