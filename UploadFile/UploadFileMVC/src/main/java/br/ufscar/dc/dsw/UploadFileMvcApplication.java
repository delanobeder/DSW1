package br.ufscar.dc.dsw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.service.spec.FilesStorageService;

@SpringBootApplication
public class UploadFileMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadFileMvcApplication.class, args);
	}

	@Bean
	CommandLineRunner run(FilesStorageService service) throws Exception {
		return args -> {
			service.init();
		};
	}

}
