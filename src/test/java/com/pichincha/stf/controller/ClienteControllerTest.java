package com.pichincha.stf.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.pichincha.stf.entity.Cliente;
import com.pichincha.stf.entity.RespuestaTo;
import com.pichincha.stf.entity.to.ClienteTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

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

		ResponseEntity<RespuestaTo> response =testRestTemplate.postForEntity("/cliente/guardar", request, RespuestaTo.class);

		ResponseEntity<ClienteTo> response1 = testRestTemplate.getForEntity("/cliente/buscar/2020662004",
				ClienteTo.class);

		HttpEntity<String> requestEliminar = new HttpEntity<>("2020662004");

		testRestTemplate.delete("/cliente/eliminar", requestEliminar);

		// assertNotNull(response.getBody().getRespuesta());
	}

	private Cliente obtenerCliente(String identificacion) {
		Cliente cliente = new Cliente();
		cliente.setIdentificacion(identificacion);
		return cliente;
	}

}
