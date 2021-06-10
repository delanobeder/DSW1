package br.ufscar.dc.dsw;

import java.io.File;
import java.nio.file.Files;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.dao.IFileDAO;
import br.ufscar.dc.dsw.domain.FileEntity;

@SpringBootApplication
public class UploadFileJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadFileJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IFileDAO dao) {
		return (args) -> {
			File file1 = new File("SIGA.pdf");
			FileEntity entity1 = new FileEntity(file1.getName(), "application/pdf", Files.readAllBytes(file1.toPath()));
			dao.save(entity1);
			
			File file2 = new File("UFSCar.png");
			FileEntity entity2 = new FileEntity(file2.getName(), "image/png", Files.readAllBytes(file2.toPath()));
			dao.save(entity2);
			
		};
	}
}
