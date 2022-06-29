/**
 * Clase: MarcaRepository.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pichincha.stf.entity.Marca;

/**
 *
 */
public interface MarcaRepository extends JpaRepository<Marca, Long> {

	Marca findByAbreviatura(String abreviatura);
	
}
