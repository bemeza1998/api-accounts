package com.bmeza.api_accounts.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name="movimiento")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Movimiento implements Serializable{

    private static final long serialVersionUID = -16763415612358L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NUMERO_CUENTA", length = 10, nullable = false)
    private String numeroCuenta;

    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "TIPO", nullable = false, length = 8)
    private String tipo;

    @Column(name = "VALOR", precision = 22, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "SALDO_POST_MOVIMIENTO", precision = 22, scale = 2, nullable = false)
    private BigDecimal saldoPostMovimiento;
    
}
