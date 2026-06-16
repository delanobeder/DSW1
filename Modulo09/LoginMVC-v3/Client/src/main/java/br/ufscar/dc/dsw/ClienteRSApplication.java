package br.ufscar.dc.dsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IRestClientService;

@SpringBootApplication
public class ClienteRSApplication {

	private static final Logger log = LoggerFactory.getLogger(ClienteRSApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ClienteRSApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(IRestClientService service) throws Exception {
		return args -> {	
			Usuario user = new Usuario("user", "user", "ROLE_USER");
			
			log.info("-----------------------------------");
			log.info("createUser()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.createUser(user) ? "OK" : "Not OK"));

			log.info("-----------------------------------");
			log.info("testeAutenticacao()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutenticacao(user.getToken()) ? "OK" : "Not OK"));

			log.info("-----------------------------------");
			log.info("login()");
			log.info("-----------------------------------");
			user.setToken(service.login(user));
			log.info("Token = " + user.getToken());

			log.info("-----------------------------------");
			log.info("testeAutenticacao()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutenticacao(user.getToken()) ? "OK" : "Not OK"));

			log.info("-----------------------------------");
			log.info("testeAutorizacaoAdmin()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutorizacaoAdmin(user.getToken()) ? "OK" : "Not OK"));

			log.info("-----------------------------------");
			log.info("testeAutorizacaoUser()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutorizacaoUser(user.getToken()) ? "OK" : "Not OK"));
			log.info("-----------------------------------");

			log.info("");
			
			Usuario admin = new Usuario("admin", "admin", "ROLE_ADMIN");
			
			log.info("-----------------------------------");
			log.info("createUser()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.createUser(admin) ? "OK" : "Not OK"));

			log.info("-----------------------------------");
			log.info("testeAutenticacao()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutenticacao(admin.getToken()) ? "OK" : "Not OK"));
			
			log.info("-----------------------------------");
			log.info("login()");
			log.info("-----------------------------------");
			admin.setToken(service.login(admin));
			log.info("Token = " + admin.getToken());

			log.info("-----------------------------------");
			log.info("testeAutenticacao()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutenticacao(admin.getToken()) ? "OK" : "Not OK"));

			log.info("-----------------------------------");
			log.info("testeAutorizacaoAdmin()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutorizacaoAdmin(admin.getToken()) ? "OK" : "Not OK"));

			log.info("-----------------------------------");
			log.info("testeAutorizacaoUser()");
			log.info("-----------------------------------");
			log.info("Resposta = " + (service.testeAutorizacaoUser(admin.getToken()) ? "OK" : "Not OK"));

		};
	}
}
