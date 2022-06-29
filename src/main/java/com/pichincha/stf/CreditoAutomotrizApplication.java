package com.pichincha.stf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pichincha.stf.service.ProcesadorCsvServicio;

@SpringBootApplication
public class CreditoAutomotrizApplication implements CommandLineRunner {

	private static final String RUTA = "static";
	private static final String CLIENTES_CSV = "clientes.csv";

	@Autowired
	private ProcesadorCsvServicio procesadorCsvServicio;

	public static void main(String[] args) {
		SpringApplication.run(CreditoAutomotrizApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		procesadorCsvServicio.cargarClientes(RUTA, CLIENTES_CSV);
	}

}
