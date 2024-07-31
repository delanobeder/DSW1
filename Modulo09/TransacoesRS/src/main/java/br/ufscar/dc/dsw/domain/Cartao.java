package br.ufscar.dc.dsw.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "transacoes" })
@Entity
@Table(name = "Cartao")
public class Cartao extends AbstractEntity<Long> {

	@NotBlank
	@Column(nullable = false, length = 80)
	private String titular;

	@NotBlank
    @Column(nullable = false, length = 14)
    private String CPF;
	
	@NotBlank
	@Column(nullable = false, unique = true, length = 19)
	private String numero;
	
	@NotBlank
	@Column(nullable = false, length = 5)
	private String vencimento;
	
	@NotBlank
	@Column(nullable = false, length = 3)
	private String CVV;
	
	@OneToMany(mappedBy = "cartao")
	private List<Transacao> transacoes;
	
	public String getTitular() {
		return titular;
	}

	public void setTitular(String nome) {
		this.titular = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getVencimento() {
		return vencimento;
	}

	public void setVencimento(String vencimento) {
		this.vencimento = vencimento;
	}

	public String getCVV() {
		return CVV;
	}

	public void setCVV(String seguranca) {
		this.CVV = seguranca;
	}

	
	public List<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	@Override
	public String toString() {
		return numero + "/" + titular;
	}
}