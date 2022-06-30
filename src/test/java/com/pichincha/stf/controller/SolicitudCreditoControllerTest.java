/**
 * Clase: VehiculoContollerTest.java
 * Fecha: 29 jun. 2022
 */

package com.pichincha.stf.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.Ejecutivo;
import com.pichincha.stf.entity.Patio;
import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.SolicitudCredito;
import com.pichincha.stf.entity.Vehiculo;
import com.pichincha.stf.entity.enumeration.EstadoSolicitudEnum;
import com.pichincha.stf.entity.to.SolicitudCreditoTo;

/**
 * Clase de test para la clase SolicitudCreditoController
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SolicitudCreditoControllerTest {

	private static final int MESES_PLAZO = 36;
	private static final int CUOTA = 350;
	private static final int ENTRADA = 6000;
	private static final String IDENTIFICACION_EJECUTIVO = "2020662004";
	private static final String PLACA = "PDM9090";
	private static final String NUMERO_PUNTO_VENTA = "123";
	private static final String IDENTIFICACION_CLIENTE = "1818661804";
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void deberiaGuardarVehiculo() {

		SolicitudCredito solicitidCredito = this.obtenerSolicitudCredito();
		SolicitudCreditoTo solicitudCreditoTo = new SolicitudCreditoTo();
		solicitudCreditoTo.setSolicitudCredito(solicitidCredito);
		solicitudCreditoTo.setIdentificacionCliente(IDENTIFICACION_CLIENTE);
		solicitudCreditoTo.setNumeroPuntoVenta(NUMERO_PUNTO_VENTA);
		solicitudCreditoTo.setPlaca(PLACA);
		solicitudCreditoTo.setIdentificacionEjecutivo(IDENTIFICACION_EJECUTIVO);

		HttpEntity<SolicitudCreditoTo> request = new HttpEntity<>(solicitudCreditoTo);

		ResponseEntity<RespuestaTo> response = testRestTemplate.postForEntity("/solicitud/credito/guardar", request,
				RespuestaTo.class);

		assertNotNull(response.getBody().getRespuesta());

	}

	private SolicitudCredito obtenerSolicitudCredito() {
		SolicitudCredito solicitudCredito = new SolicitudCredito();
		solicitudCredito.setFechaElaboracion(LocalDate.now());
		solicitudCredito.setEntrada(new BigDecimal(ENTRADA));
		solicitudCredito.setCuota(new BigDecimal(CUOTA));
		solicitudCredito.setMesesPlazo(MESES_PLAZO);
		solicitudCredito.setCliente(obtenerCliente());
		solicitudCredito.setPatio(obtenerPatio());
		solicitudCredito.setVehiculo(obtenerVehiculo());
		solicitudCredito.setEjecutivo(obtenerEjecutivo());
		solicitudCredito.setEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA);
		return solicitudCredito;
	}

	private Cliente obtenerCliente() {
		Cliente cliente = new Cliente();
		cliente.setIdentificacion(IDENTIFICACION_CLIENTE);
		cliente.setNombre("Alejanro");
		cliente.setApellido("Donoso");
		cliente.setEdad(34);
		cliente.setDireccion("Avigiras");
		cliente.setTelefono("022999999");
		return cliente;
	}

	private Ejecutivo obtenerEjecutivo() {
		Ejecutivo ejecutivo = new Ejecutivo();
		ejecutivo.setIdentificacion(IDENTIFICACION_EJECUTIVO);
		ejecutivo.setNombre("Juana");
		ejecutivo.setApellido("de Arco");
		ejecutivo.setEdad(34);
		ejecutivo.setDireccion("De los Guayacanes");
		ejecutivo.setTelefono("022888888");
		ejecutivo.setPatio(obtenerPatio());
		return ejecutivo;
	}

	private Patio obtenerPatio() {
		Patio patio = new Patio();
		patio.setNumeroPuntoVenta(NUMERO_PUNTO_VENTA);
		return patio;
	}

	private Vehiculo obtenerVehiculo() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(PLACA);
		return vehiculo;
	}

}
