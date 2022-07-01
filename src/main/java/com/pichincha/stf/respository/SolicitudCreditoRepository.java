/**
 * Clase: SolicitudCreditoRepository.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.respository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.SolicitudCredito;

/**
 *
 */
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, Long> {

	@Query("SELECT sc FROM SolicitudCredito sc WHERE sc.cliente.identificacion = :identificacion "
			+ " AND sc.fechaElaboracion = :fechaElaboracion ")
	SolicitudCredito obtenerSolicituPorClienteFecha(@Param("identificacion") String identifcacionCliente,
			@Param("fechaElaboracion") LocalDate fechaElaboracion);

	@Query("SELECT sc FROM SolicitudCredito sc WHERE sc.cliente = :cliente")
	Optional<List<SolicitudCredito>> obtenerSolicitudesPorCliente(@Param("cliente") Cliente cliente);

	@Query("SELECT sc FROM SolicitudCredito sc WHERE sc.patio.numeroPuntoVenta = :numeroPuntoVenta "
			+ " AND sc.cliente.identificacion = :identificacion")
	Optional<List<SolicitudCredito>> obtenerSolicitudesPorNumeroPuntoVentaIdentificacion(
			@Param("numeroPuntoVenta") String numeroPuntoVenta, @Param("identificacion") String identificacion);

	@Query("SELECT sc FROM SolicitudCredito sc WHERE sc.patio = :patio ")
	Optional<List<SolicitudCredito>> obtenerSolicitudesPorPatio(@Param("patio") Patio patio);

}
