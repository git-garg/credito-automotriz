/**
 * Clase: VehiculoContollerTest.java
 * Fecha: 29 jun. 2022
 */
package com.pichincha.stf.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoVehiculoEnum;
import com.pichincha.stf.entity.to.VehiculoTo;

/**
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehiculoContollerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private Vehiculo vehiculo;
	private VehiculoTo vehiculoTo;
	private HttpEntity<VehiculoTo> request;

	public void cargarIniciales() {
		this.vehiculo = this.obtenerVehiculo();
		this.vehiculoTo = new VehiculoTo();
		vehiculoTo.setVehiculo(this.vehiculo);
		vehiculoTo.setAbreviaturaMarca("KIA");
		this.request = new HttpEntity<>(vehiculoTo);
	}

	@Test
	public void deberiaGuardarVehiculo() {

		cargarIniciales();

		ResponseEntity<RespuestaTo> response = testRestTemplate.postForEntity("/vehiculo/guardar", request,
				RespuestaTo.class);

		assertNotNull(response.getBody().getRespuesta());

	}

	@Test
	public void deberiaObtenerVehiculosPorMarca() {

		cargarIniciales();

		ResponseEntity<VehiculoTo[]> response = testRestTemplate.getForEntity("/vehiculo/buscar/por/marca/KIA",
				VehiculoTo[].class);

		VehiculoTo[] vehiculos = response.getBody();
		assertEquals("KIA", vehiculos[0].getVehiculo().getMarca().getAbreviatura());

	}

	@Test
	public void deberiaObtenerVehiculosPorModelo() {
		cargarIniciales();

		testRestTemplate.postForEntity("/vehiculo/guardar", request, RespuestaTo.class);

		ResponseEntity<VehiculoTo[]> response = testRestTemplate.getForEntity("/vehiculo/buscar/por/modelo/rio",
				VehiculoTo[].class);

		VehiculoTo[] vehiculos = response.getBody();
		assertEquals("rio", vehiculos[0].getVehiculo().getModelo());

	}

	private Vehiculo obtenerVehiculo() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setAvaluo(new BigDecimal("10000"));
		vehiculo.setCilindraje("1200");
		vehiculo.setCodigoVehiculo(2L);
		vehiculo.setEstadoVehiculo(EstadoVehiculoEnum.DISPONIBLE);
		vehiculo.setModelo("rio");
		vehiculo.setNumeroChasis("POIUYGBHNM");
		vehiculo.setPlaca("PDK9841");
		vehiculo.setAnio(2020);
		return vehiculo;
	}

}
