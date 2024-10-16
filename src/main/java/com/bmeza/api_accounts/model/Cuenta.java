package com.bmeza.api_accounts.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cuenta")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cuenta implements Serializable{

    private static final long serialVersionUID = -167634057102358L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_CLIENTE", length = 8, nullable = false)
    private String idCliente;

    @Column(name = "NUMERO_CUENTA", length = 10, nullable = false)
    private String numeroCuenta;

    @Column(name = "TIPO", nullable = false, length = 12)
    private String tipo;

    @Column(name = "SALDO_INICIAL", precision = 22, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "SALDO", precision = 22, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "ESTADO", nullable = false, length = 8)
    private String estado;
    
}
