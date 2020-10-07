package br.ufscar.dc.dsw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Professor")
public class Professor extends Pessoa {
	
	@Column(nullable = false)
	private float salario;
	
	@ManyToOne
	@JoinColumn(name = "departamento_id")
	private Departamento departamento;

	@OneToOne(mappedBy = "professor")
	private Disciplina disciplina;
	
	public Professor() {
		
	}
	
	public Professor(String nome, float salario, Departamento departamento) {
		super(nome);
		this.salario = salario;
		this.departamento = departamento;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	@Override
	public String toString() {
		return "[Nome = " + this.getNome() + ", Salário = " + salario + ", Departamento = " + departamento + "]";
	}
}
