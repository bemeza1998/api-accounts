package com.bmeza.api_accounts.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bmeza.api_accounts.model.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{

     @Query(value = "SELECT " +
                   "c.nombre AS nombre_cliente, " +
                   "c.identificacion AS identificacion_cliente, " +
                   "cu.numero_cuenta, " +
                   "cu.tipo AS tipo_cuenta, " +
                   "cu.saldo_inicial, " +
                   "m.fecha, " +
                   "m.saldo_post_movimiento, " +
                   "m.valor AS valor_movimiento, " +
                   "m.tipo AS tipo_movimiento " +
                   "FROM cliente c " +
                   "JOIN cuenta cu ON c.id_persona = cu.id_persona " +
                   "JOIN movimiento m ON cu.numero_cuenta = m.numero_cuenta " +
                   "WHERE c.id_persona = :idPersona " + 
                   "AND m.fecha > :fechaInicial AND m.fecha < :fechaFinal " +
                   "ORDER BY m.fecha DESC, m.numero_cuenta",
           nativeQuery = true)
    List<Object[]> findMovimientosByCliente(
        @Param("idPersona") String idPersona,
        @Param("fechaInicial") Date fechaInicial,
        @Param("fechaFinal") Date fechaFinal
        );
    
}
