package com.pichincha.stf;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.opencsv.exceptions.CsvException;
import com.pichincha.stf.service.ClienteServicio;
import com.pichincha.stf.service.EjecutivoServicio;
import com.pichincha.stf.service.MarcaServicio;
import com.pichincha.stf.service.PatioServicio;
import com.pichincha.stf.service.exception.ClienteException;
import com.pichincha.stf.service.exception.CreditoAutomotrizException;

@SpringBootApplication
public class CreditoAutomotrizApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(CreditoAutomotrizApplication.class);

	private static final String RUTA = "static";
	private static final String CLIENTES_CSV = "clientes.csv";
	private static final String EJECUTIVOS_CSV = "ejecutivos.csv";
	private static final String MARCAS_CSV = "marcas.csv";

	@Autowired
	private ClienteServicio clienteServicio;

	@Autowired
	private PatioServicio patioServicio;

	@Autowired
	private EjecutivoServicio ejecutivoServicio;

	@Autowired
	private MarcaServicio marcaServicio;

	public static void main(String[] args) {
		SpringApplication.run(CreditoAutomotrizApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			clienteServicio.cargarClientes(RUTA, CLIENTES_CSV);
			patioServicio.cargarPatios();
			ejecutivoServicio.cargarEjecutivos(RUTA, EJECUTIVOS_CSV);
			marcaServicio.cargarMarcas(RUTA, MARCAS_CSV);
		} catch (IOException | URISyntaxException | CsvException | ClienteException | CreditoAutomotrizException e) {
			log.error(e.getMessage());
		}
	}

}
