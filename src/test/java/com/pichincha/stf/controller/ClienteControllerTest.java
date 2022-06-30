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

		Cliente cliente = this.obtenerCliente();
		ClienteTo clienteTo = new ClienteTo();
		clienteTo.setCliente(cliente);
		HttpEntity<ClienteTo> request = new HttpEntity<>(clienteTo);

		ResponseEntity<RespuestaTo> response = testRestTemplate.postForEntity("/cliente/guardar", request,
				RespuestaTo.class);

		assertNotNull(response.getBody().getRespuesta());

	}

	private Cliente obtenerCliente() {
		Cliente cliente = new Cliente();
		cliente.setIdentificacion("1717661704");
		return cliente;
	}

}
