/**
 * Clase: SolicitudCredito.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.pichincha.stf.entity.enumeration.EstadoSolicitudEnum;

import lombok.Data;

/**
 * Clase entidad que reprsenta la tabla solicitud credito
 */
@Entity
@Table
@Data
public class SolicitudCredito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_solicitud_credito")
	private Long codigoSolicitudCredito;

	@Column(name = "fecha_elaboracion")
	@NotNull(message = "El campo fecha elaboración es obligatorio")
	private LocalDate fechaElaboracion;

	@Column
	@NotNull(message = "El campo entrada es obligatorio")
	private BigDecimal entrada;

	@Column
	@NotNull(message = "El campo cuota es obligatorio")
	private BigDecimal cuota;

	@Column(name = "meses_plazo")
	@NotNull(message = "El campo meses plazo es obligatorio")
	private Integer mesesPlazo;

	@Column
	private String observacion;

	@ManyToOne
	@JoinColumn(name = "codigo_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "codigo_patio", nullable = false)
	private Patio patio;

	@ManyToOne
	@JoinColumn(name = "codigo_vehiculo", nullable = false)
	private Vehiculo vehiculo;

	@ManyToOne
	@JoinColumn(name = "codigo_ejecutivo", nullable = false)
	private Ejecutivo ejecutivo;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_solicitud")
	private EstadoSolicitudEnum estadoSolicitud;

}
