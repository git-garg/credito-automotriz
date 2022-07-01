package com.pichincha.stf.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.enumeration.EstadoCivilEnum;
import com.pichincha.stf.entity.enumeration.SiNoEnum;
import com.pichincha.stf.entity.to.ClienteTo;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;
import com.pichincha.stf.util.FechaUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private static final Logger log = LoggerFactory.getLogger(ClienteControllerTest.class);

	@Test
	public void deberiaGuardarCliente() {

		Cliente cliente = this.obtenerCliente("1717661704");
		ClienteTo clienteTo = new ClienteTo();
		clienteTo.setCliente(cliente);
		HttpEntity<ClienteTo> request = new HttpEntity<>(clienteTo);

		ResponseEntity<RespuestaTo> response = testRestTemplate.postForEntity("/cliente/guardar", request,
				RespuestaTo.class);

		assertNotNull(response.getBody().getRespuesta());

	}

	@Test
	private void deberiaActualizar() {

		Cliente cliente = this.obtenerCliente("1818661804");
		cliente.setApellido("Reyes");
		ClienteTo clienteTo = new ClienteTo(cliente);

		HttpEntity<ClienteTo> request = new HttpEntity<>(clienteTo);

		ResponseEntity<RespuestaTo> response = testRestTemplate.postForEntity("/cliente/actualizar", request,
				RespuestaTo.class);

		assertNotNull(response.getBody().getRespuesta());
	}

	@Test
	public void deberiaEliminar() {

		Cliente cliente = this.obtenerCliente("2020662004");
		ClienteTo clienteTo = new ClienteTo();
		clienteTo.setCliente(cliente);
		HttpEntity<ClienteTo> request = new HttpEntity<>(clienteTo);

		testRestTemplate.postForEntity("/cliente/guardar", request, RespuestaTo.class);

		testRestTemplate.delete("/cliente/eliminar?identificacion", "2020662004");

		ResponseEntity<ClienteTo> consulta = testRestTemplate.getForEntity("/cliente/buscar/2020662004",
				ClienteTo.class);

		assertNotNull(consulta.getBody().getCliente());
	}

	private Cliente obtenerCliente(String identificacion) {
		Cliente cliente = new Cliente();
		cliente.setIdentificacion(identificacion);
		cliente.setNombre("Gabo");
		cliente.setApellido("Quintana");
		cliente.setEdad(36);
		cliente.setDireccion("El Inca");
		cliente.setTelefono("098756321");
		try {
			cliente.setFechaNacimiento(FechaUtil.obtenerFechaFormato("10/05/1990", "dd/MM/yyyy"));
		} catch (CreditoAutomotrizException e) {
			log.info(e.getMessage());
		}
		cliente.setEstadoCivil(EstadoCivilEnum.SOLTERO);
		cliente.setIdentificacionConyugue("-");
		cliente.setNombreConyugue("-");
		cliente.setSujetoCredito(SiNoEnum.SI);
		return cliente;
	}

}
