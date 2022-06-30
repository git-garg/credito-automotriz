/**
 * Clase: SolicitudCreditoRepository.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pichincha.stf.entity.SolicitudCredito;

/**
 *
 */
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, Long> {
	
	
}
