package br.ufscar.dc.dsw;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IEditoraDAO;
import br.ufscar.dc.dsw.dao.ILivroDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Editora;
import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class LivrariaMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, BCryptPasswordEncoder encoder, IEditoraDAO editoraDAO, ILivroDAO livroDAO) {
		return (args) -> {
			
			Usuario u1 = new Usuario();
			u1.setUsername("admin");
			u1.setPassword(encoder.encode("admin"));
			u1.setCPF("012.345.678-90");
			u1.setName("Administrador");
			u1.setRole("ROLE_ADMIN");
			u1.setEnabled(true);
			usuarioDAO.save(u1);
			
			Usuario u2 = new Usuario();
			u2.setUsername("beltrano");
			u2.setPassword(encoder.encode("123"));
			u2.setCPF("985.849.614-10");
			u2.setName("Beltrano Andrade");
			u2.setRole("ROLE_USER");
			u2.setEnabled(true);
			usuarioDAO.save(u2);
			
			Usuario u3 = new Usuario();
			u3.setUsername("fulano");
			u3.setPassword(encoder.encode("123"));
			u3.setCPF("367.318.380-04");
			u3.setName("Fulano Silva");
			u3.setRole("ROLE_USER");
			u3.setEnabled(true);
			usuarioDAO.save(u3);
			
			Editora e1 = new Editora();
			e1.setCNPJ("55.789.390/0008-99");
			e1.setNome("Companhia das Letras");
			editoraDAO.save(e1);
			
			Editora e2 = new Editora();
			e2.setCNPJ("71.150.470/0001-40");
			e2.setNome("Record");
			editoraDAO.save(e2);
			
			Editora e3 = new Editora();
			e3.setCNPJ("32.106.536/0001-82");
			e3.setNome("Objetiva");
			editoraDAO.save(e3);
			
			Livro l1 = new Livro();
			l1.setTitulo("Ensaio sobre a Cegueira");
			l1.setAutor("José Saramago");
			l1.setAno(1995);
			l1.setPreco(BigDecimal.valueOf(54.9));
			l1.setEditora(e1);
			livroDAO.save(l1);
			
			Livro l2 = new Livro();
			l2.setTitulo("Cem anos de Solidão");
			l2.setAutor("Gabriel Garcia Márquez");
			l2.setAno(1977);
			l2.setPreco(BigDecimal.valueOf(59.9));
			l2.setEditora(e2);
			livroDAO.save(l2);
			
			Livro l3 = new Livro();
			l3.setTitulo("Diálogos Impossíveis");
			l3.setAutor("Luis Fernando Verissimo");
			l3.setAno(2012);
			l3.setPreco(BigDecimal.valueOf(22.9));
			l3.setEditora(e3);
			livroDAO.save(l3);
		};
	}
}
