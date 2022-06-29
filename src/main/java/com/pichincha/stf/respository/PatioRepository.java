/**
 * Clase: PatioRepository.java
 * Fecha: 28 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.stf.entity.Patio;

/**
 *
 */

@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {

	Patio findByNumeroPuntoVenta(String numeroPuntoVenta);

}
