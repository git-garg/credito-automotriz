/**
 * Clase: SolicitudCreditoRepository.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.respository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pichincha.stf.entity.SolicitudCredito;

/**
 *
 */
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, Long> {
	
	@Query("SELECT sc FROM SolicitudCredito sc WHERE sc.cliente.identificacion = :identificacion "
			+ " AND sc.fechaElaboracion = :fechaElaboracion ")
	SolicitudCredito obtenerSolicituPorClienteFecha(@Param("identificacion") String identifcacionCliente,
			@Param("fechaElaboracion") LocalDate fechaElaboracion);

}
