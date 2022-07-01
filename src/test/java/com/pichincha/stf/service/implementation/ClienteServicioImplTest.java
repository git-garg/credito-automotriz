package com.pichincha.stf.service.implementation;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.respository.ClienteRepository;
import com.pichincha.stf.respository.SolicitudCreditoRepository;
import com.pichincha.stf.service.ProcesadorCsvServicio;

@SpringBootTest
public class ClienteServicioImplTest {

	private static final long DOS = 2L;

	@InjectMocks
	private ClienteServicioImpl clienteServicioImpl;

	@Mock
	private ProcesadorCsvServicio procesadorCsvServicio;

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private SolicitudCreditoRepository solicitudCreditoRepository;

	@Test
	public void deberiaObtenerTotalClientes() throws IOException, URISyntaxException, CsvException {
		when(procesadorCsvServicio.obtenerRegistrosArchivoCsv(anyString(), anyString())).thenReturn(obtenerLsta());
		when(clienteRepository.count()).thenReturn(DOS);
		Long listsClientes = clienteServicioImpl.cargarClientes(anyString(), anyString());
		assertTrue(listsClientes.equals(DOS));

	}

	private List<String[]> obtenerLsta() {

		String[] registroUno = { "1717661704", "Gabriel Alejandro", "Reyes Guaman", "36",
				"Avigiras y de los Guayacanes", "0981199652", "09/04/1986", "SOL", "0236987541", "Juan Perez", "S" };
		String[] registroDos = { "1714509021", "Nelson Pul", "Reyes Guaman", "38", "Piqueros y Mirasierra", "098185263",
				"03/10/1983", "SOL", "0236987541", "Juana de Arco", "S" };
		List<String[]> lista = new ArrayList<String[]>();
		lista.add(registroUno);
		lista.add(registroDos);
		return lista;
	}
}
