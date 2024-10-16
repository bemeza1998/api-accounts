package com.bmeza.api_accounts.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovimientoDTO {

    private String numeroCuenta;
    private BigDecimal valor;

}
