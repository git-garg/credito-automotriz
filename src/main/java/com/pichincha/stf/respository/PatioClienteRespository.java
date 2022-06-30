/**
 * Clase: PatioClienteRespository.java
 * Fecha: 30 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pichincha.stf.entity.PatioCliente;

/**
 *
 */
public interface PatioClienteRespository extends JpaRepository<PatioCliente, Long> {	
}
