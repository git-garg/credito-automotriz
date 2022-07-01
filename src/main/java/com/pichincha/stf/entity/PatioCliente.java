/**
 * Clase: PatioCliente.java
 * Fecha: 30 jun. 2022
 */
package com.pichincha.stf.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 *
 */
@Entity
@Table
@Data
public class PatioCliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_patio_cliente")
	private Long codigoPatioCliente;

	@Column(name = "fecha_asignacion")
	@NotNull(message = "El campo fecha asignación es obligatorio")
	private LocalDate fechaAsignacion;

	@ManyToOne
	@NotNull(message = "El cliente es obligatorio")
	@JoinColumn(name = "codigo_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne
	@NotNull(message = "El patio es obligatorio")
	@JoinColumn(name = "codigo_patio", nullable = false)
	private Patio patio;

}
