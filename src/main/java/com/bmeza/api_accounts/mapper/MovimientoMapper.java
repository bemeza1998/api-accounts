package com.bmeza.api_accounts.mapper;

import lombok.NoArgsConstructor;

import java.util.Date;

import com.bmeza.api_accounts.dto.MovimientoDTO;
import com.bmeza.api_accounts.model.Movimiento;

import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MovimientoMapper {

    public static Movimiento construirMovimiento(MovimientoDTO dto) {

        return Movimiento.builder()
                .fecha(new Date())
                .tipo(dto.getValor().signum() == -1 ? "RETIRO" : "DEPOSITO")
                .valor(dto.getValor())
                .saldoPostMovimiento(null)
                .numeroCuenta(dto.getNumeroCuenta())
                .build();

    }

}
