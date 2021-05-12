package br.ufscar.dc.dsw;

import java.io.File;

import javax.mail.internet.InternetAddress;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEmailApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(EmailService service) {
		return (args) -> {

            InternetAddress from = new InternetAddress("<username>@gmail.com", "Fulano");
		    InternetAddress to = new InternetAddress("<email>@<dominio>", "Beltrano");
					
			String subject1 = "Exemplo Subject (Gmail SMTP/Spring)";
			String subject2 = "Exemplo Subject com Anexo (Gmail SMTP/Spring)";

			String body1 = "Exemplo mensagem (Gmail SMTP/Spring)";
			String body2 = "Exemplo mensagem com Anexo (Gmail SMTP/Spring)";

			// Envio sem anexo
			service.send(from, to, subject1, body1);

			// Envio com anexo
			service.send(from, to, subject2, body2, new File("SIGA.pdf"));
		};
	}
}
