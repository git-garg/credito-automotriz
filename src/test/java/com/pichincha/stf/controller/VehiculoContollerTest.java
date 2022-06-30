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

	@Test
	public void deberiaGuardarVehiculo() {

		Vehiculo vehiculo = this.obtenerVehiculo();
		VehiculoTo vehiculoTo = new VehiculoTo();
		vehiculoTo.setVehiculo(vehiculo);
		vehiculoTo.setAbreviaturaMarca("KIA");

		HttpEntity<VehiculoTo> request = new HttpEntity<>(vehiculoTo);
		ResponseEntity<RespuestaTo> response = testRestTemplate.postForEntity("/vehiculo/guardar", request,
				RespuestaTo.class);

		assertNotNull(response.getBody().getRespuesta());

	}

	@Test
	public void deberiaObtenerVehiculos() {

		ResponseEntity<VehiculoTo[]> response = testRestTemplate.getForEntity("/vehiculo/buscar/KIA",
				VehiculoTo[].class);

		VehiculoTo[] vehiculos = response.getBody();
		assertEquals("KIA", vehiculos[0].getVehiculo().getMarca().getAbreviatura());

	}

	private Vehiculo obtenerVehiculo() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setAvaluo(new BigDecimal("10000"));
		vehiculo.setCilindraje("1200");
		vehiculo.setCodigoVehiculo(2L);
		vehiculo.setEstadoVehiculo(EstadoVehiculoEnum.DISPONIBLE);
		vehiculo.setModelo("Sportage");
		vehiculo.setNumeroChasis("POIUYGBHNM");
		vehiculo.setPlaca("PDK9841");
		vehiculo.setModelo("Rio");
		return vehiculo;
	}

}
