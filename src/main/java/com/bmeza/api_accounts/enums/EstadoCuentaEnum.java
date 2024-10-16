package com.bmeza.api_accounts.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum EstadoCuentaEnum {
    
    ACTIVA("ACT", "Activa"),
    INACTIVA("INA", "Inactiva");
  
    private final String valor;
    private final String texto;

}
