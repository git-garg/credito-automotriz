/**
 * Clase: EjecutivoRepository.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.stf.entity.Ejecutivo;

/**
 *
 */

@Repository
public interface EjecutivoRepository extends JpaRepository<Ejecutivo, Long> {

}