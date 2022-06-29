package com.pichincha.stf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pichincha.stf.service.ClienteServicio;
import com.pichincha.stf.service.EjecutivoServicio;
import com.pichincha.stf.service.PatioServicio;

@SpringBootApplication
public class CreditoAutomotrizApplication implements CommandLineRunner {

	private static final String RUTA = "static";
	private static final String CLIENTES_CSV = "clientes.csv";
	private static final String EJECUTIVOS_CSV = "ejecutivos.csv";

	@Autowired
	private ClienteServicio clienteServicio;

	@Autowired
	private PatioServicio patioServicio;

	@Autowired
	private EjecutivoServicio ejecutivoServicio;

	public static void main(String[] args) {
		SpringApplication.run(CreditoAutomotrizApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		clienteServicio.cargarClientes(RUTA, CLIENTES_CSV);
		patioServicio.cargarPatios();
		ejecutivoServicio.cargarEjecutivos(RUTA, EJECUTIVOS_CSV);
	}

}
