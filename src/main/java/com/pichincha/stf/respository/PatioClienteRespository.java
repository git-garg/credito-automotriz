/**
 * Clase: PatioClienteRespository.java
 * Fecha: 30 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pichincha.stf.entity.PatioCliente;

/**
 *
 */
public interface PatioClienteRespository extends JpaRepository<PatioCliente, Long> {

	@Query("SELECT pc FROM PatioCliente pc WHERE pc.patio.numeroPuntoVenta = :numeroPuntoVenta "
			+ " AND pc.cliente.identificacion = :identificacion ")
	PatioCliente obtenerPatioClientePorNumeroIdentificacion(@Param("numeroPuntoVenta") String numeroPuntoVenta,
			@Param("identificacion") String identificacion);
}
