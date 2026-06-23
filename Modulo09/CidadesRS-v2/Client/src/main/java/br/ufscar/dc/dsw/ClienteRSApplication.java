package br.ufscar.dc.dsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClienteRSApplication {

	private static final Logger log = LoggerFactory.getLogger(ClienteRSApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClienteRSApplication.class, args);
	}
}
