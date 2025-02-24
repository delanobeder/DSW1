package br.ufscar.dc.dsw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
