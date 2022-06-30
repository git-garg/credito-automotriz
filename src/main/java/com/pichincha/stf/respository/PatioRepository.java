/**
 * Clase: PatioRepository.java
 * Fecha: 28 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pichincha.stf.entity.Patio;

/**
 *
 */

@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {

	Patio findByNumeroPuntoVenta(String numeroPuntoVenta);

	@Query("SELECT p FROM Patio p WHERE p.numeroPuntoVenta = :numeroPuntoVenta")
	Patio obtenerPorNumeroPuntoVenta(@Param("numeroPuntoVenta") String numeroPuntoVenta);

}
