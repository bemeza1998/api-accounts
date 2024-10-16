package com.bmeza.api_accounts.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ResponseEntity<Cuenta> buscarCuenta(@RequestParam String numeroCuenta){
        Cuenta Cuenta = this.service.buscarCuenta(numeroCuenta);
        return ResponseEntity.ok(Cuenta);
    }

    @PatchMapping
    public ResponseEntity<String> modificarCuenta(@RequestBody Cuenta Cuenta){
        this.service.modificarCuenta(Cuenta);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> eliminarCuenta(@RequestParam String numeroCuenta){
        this.service.eliminarCuenta(numeroCuenta);
        return ResponseEntity.ok().build();
    }
    
}
