package br.ufscar.dc.dsw.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Disciplina")
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String nome;

	@Column(nullable = false, unique = true, length = 5)
	private String sigla;

	@ManyToMany(targetEntity = Aluno.class, mappedBy = "disciplinas", fetch = FetchType.EAGER)
	private Set<Aluno> alunos;

	@OneToOne
	private Professor professor;

	public Disciplina() {	
	}
	
	public Disciplina(String nome, String sigla, Professor professor) {
		this.nome = nome;
		this.sigla = sigla;
		this.professor = professor;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Set<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	public void addAluno(Aluno a) {
		this.alunos.add(a);
	}

	@Override
	public String toString() {
		return "[Nome = " + nome + ", Sigla = " + sigla + "]";
	}
}