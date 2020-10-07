package br.ufscar.dc.dsw;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.dao.IDepartamentoDAO;
import br.ufscar.dc.dsw.dao.IDisciplinaDAO;
import br.ufscar.dc.dsw.dao.IPessoaDAO;
import br.ufscar.dc.dsw.dao.IProfessorDAO;
import br.ufscar.dc.dsw.domain.Aluno;
import br.ufscar.dc.dsw.domain.Departamento;
import br.ufscar.dc.dsw.domain.Disciplina;
import br.ufscar.dc.dsw.domain.Pessoa;
import br.ufscar.dc.dsw.domain.Professor;

@SpringBootApplication
public class SpringDataJpaApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringDataJpaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IPessoaDAO pessoaDAO, IDepartamentoDAO departamentoDAO, IDisciplinaDAO disciplinaDAO, IProfessorDAO professorDAO) {
		return (args) -> {
			
			Departamento dc = new Departamento("Computação", "DC");

			log.info("Salvando Departamento - DC");

			departamentoDAO.save(dc);

			Professor professor1 = new Professor("Professor 1", 1000, dc);

			log.info("Salvando Professor 1");

			pessoaDAO.save(professor1);

			Professor professor2 = new Professor("Professor 2", 3000, dc);

			log.info("Salvando Professor 2");

			pessoaDAO.save(professor2);
			
			Disciplina dsw = new Disciplina("Desenvolvimento Web 1", "DSW", professor1);

			log.info("Salvando Disciplina - DSW");

			disciplinaDAO.save(dsw);

			Aluno aluno1 = new Aluno("Aluno 1", "123456");

			log.info("Salvando Aluno 1");

			pessoaDAO.save(aluno1);

			Aluno aluno2 = new Aluno("Aluno 2", "654321");

			log.info("Salvando Aluno 2");

			pessoaDAO.save(aluno2);

			List<Pessoa> pessoas = pessoaDAO.findAll();

			log.info("Imprimindo pessoas - findAll()");

			for (Pessoa p : pessoas) {
				log.info(p.toString());
			}

			// Matricula aluno1 na disciplina

			Set<Disciplina> disciplinas = new HashSet<>();
			disciplinas.add(dsw);
			aluno1.setDisciplinas(disciplinas);

			pessoaDAO.save(aluno1);
			
			// Matricula aluno2 na disciplina
			
			disciplinas = new HashSet<>();
			disciplinas.add(dsw);
			aluno2.setDisciplinas(disciplinas);

			pessoaDAO.save(aluno2);

			log.info("Imprimindo alunos da disciplina dsw");

			dsw = disciplinaDAO.findById(1L);
			
			for (Aluno a : dsw.getAlunos()) {
				log.info(a.toString());
			}
			
			log.info("Imprimindo professores do departamento dc");
			
			for (Professor p : professorDAO.findByDepartamento(dc)) {
				log.info(p.toString());
			}
		};
	}
}
