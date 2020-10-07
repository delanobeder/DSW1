package br.ufscar.dc.dsw;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufscar.dc.dsw.dao.DepartamentoDAO;
import br.ufscar.dc.dsw.dao.DisciplinaDAO;
import br.ufscar.dc.dsw.dao.GenericDAO;
import br.ufscar.dc.dsw.dao.PessoaDAO;
import br.ufscar.dc.dsw.dao.ProfessorDAO;
import br.ufscar.dc.dsw.domain.Aluno;
import br.ufscar.dc.dsw.domain.Departamento;
import br.ufscar.dc.dsw.domain.Disciplina;
import br.ufscar.dc.dsw.domain.Pessoa;
import br.ufscar.dc.dsw.domain.Professor;

public class App {

	public static void main(String[] args) {

		PessoaDAO pessoaDAO = new PessoaDAO();

		DepartamentoDAO departamentoDAO = new DepartamentoDAO();

		DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

		ProfessorDAO professorDAO = new ProfessorDAO();

		Departamento dc = new Departamento("Computação", "DC");

		System.out.println("Salvando Departamento - DC");

		departamentoDAO.save(dc);

		Professor professor1 = new Professor("Professor 1", 1000, dc);

		System.out.println("Salvando Professor 1");

		pessoaDAO.save(professor1);

		Professor professor2 = new Professor("Professor 2", 3000, dc);

		System.out.println("Salvando Professor 2");

		pessoaDAO.save(professor2);

		Disciplina dsw = new Disciplina("Desenvolvimento Web 1", "DSW", professor1);

		System.out.println("Salvando Disciplina - DSW");

		disciplinaDAO.save(dsw);

		Aluno aluno1 = new Aluno("Aluno 1", "123456");

		System.out.println("Salvando Aluno 1");

		pessoaDAO.save(aluno1);

		Aluno aluno2 = new Aluno("Aluno 2", "654321");

		System.out.println("Salvando Aluno 2");

		pessoaDAO.save(aluno2);

		List<Pessoa> pessoas = pessoaDAO.findAll();

		System.out.println("Imprimindo pessoas - findAll()");

		for (Pessoa p : pessoas) {
			System.out.println(p);
		}

		// Matricula aluno1 na disciplina

		Set<Disciplina> disciplinas = new HashSet<>();
		disciplinas.add(dsw);
		aluno1.setDisciplinas(disciplinas);

		pessoaDAO.update(aluno1);

		// Matricula aluno2 na disciplina

		disciplinas = new HashSet<>();
		disciplinas.add(dsw);
		aluno2.setDisciplinas(disciplinas);

		pessoaDAO.update(aluno2);

		System.out.println("Imprimindo alunos da disciplina dsw");

		dsw = disciplinaDAO.find(1L);

		for (Aluno a : dsw.getAlunos()) {
			System.out.println(a);
		}

		System.out.println("Imprimindo professores do departamento dc");

		for (Professor p : professorDAO.findbyDepartamento(dc)) {
			System.out.println(p);
		}

		GenericDAO.close();
	}
}