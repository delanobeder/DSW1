package br.ufscar.dc.dsw;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
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
			
			Estado sp = new Estado(26L, "SP", "São Paulo");
			Cidade cidade = new Cidade("Teste Cidade", sp);
			log.info("-----------------------------------");
			log.info("save()");
			log.info("-----------------------------------");
			Long id = service.create(cidade);
			log.info("Salvo Cidade [" + id + "] " + cidade.toString());
			log.info("-----------------------------------");
			log.info("getCidades()");
			log.info("-----------------------------------");
			List<Cidade> cidades = service.get();
			for (Cidade c : cidades) {
				log.info(c.toString());
			}
			log.info("-----------------------------------");
			log.info("Numero de Cidades: " + cidades.size());
			log.info("-----------------------------------");
			log.info("getCidade (" + id + ")");
			log.info("-----------------------------------");
			cidade = service.get(id);
			log.info(cidade.toString());
			log.info("-----------------------------------");
			log.info("getCidades(sp)");
			log.info("-----------------------------------");
			cidades = service.get(sp);
			for (Cidade c : cidades) {
				log.info(c.toString());
			}
			log.info("-----------------------------------");
			log.info("Numero de Cidades: " + cidades.size());
			log.info("-----------------------------------");
			cidade.setNome("Teste Cidade atualizada");
			boolean ok = service.update(cidade);
			log.info("update (" + id + ") " + ok);
			log.info("-----------------------------------");
			log.info("getCidade (" + id + ")");
			log.info("-----------------------------------");
			cidade = service.get(id);
			log.info(cidade.toString());
			log.info("-----------------------------------");
			ok = service.delete(id);
			log.info("remove (" + id + ") " + ok);
			log.info("-----------------------------------");
			cidades = service.get();
			log.info("Numero de Cidades: " + cidades.size());
			log.info("-----------------------------------");
		};
	}
}
