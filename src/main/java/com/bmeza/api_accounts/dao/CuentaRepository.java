package com.bmeza.api_accounts.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmeza.api_accounts.model.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, String>{

    Optional<Cuenta> findByNumeroCuenta (String numeroCuenta);
    
}
