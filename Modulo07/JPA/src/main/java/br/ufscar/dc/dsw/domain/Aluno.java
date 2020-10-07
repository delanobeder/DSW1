package br.ufscar.dc.dsw.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Aluno")
public class Aluno extends Pessoa {

	@Column(nullable = false, unique = true, length = 6)
	private String RA;

	@ManyToMany(targetEntity = Disciplina.class)
	private Set<Disciplina> disciplinas;

	public Aluno() {
	}
	
	public Aluno(String nome, String rA) {
		super(nome);
		RA = rA;
	}

	public String getRA() {
		return RA;
	}

	public void setRA(String rA) {
		RA = rA;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	@Override
	public String toString() {
		return "[Nome = " + this.getNome() + ", RA = " + RA + "]";
	}
}
