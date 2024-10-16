package com.bmeza.api_accounts.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoEstadoCuentaDTO {

    private String nombreCliente;
    private String identificacionCliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Date fechaMovimiento;
    private BigDecimal saldoPostMovimiento;
    private BigDecimal valorMovimiento;
    private String tipoMovimiento;
    
}
