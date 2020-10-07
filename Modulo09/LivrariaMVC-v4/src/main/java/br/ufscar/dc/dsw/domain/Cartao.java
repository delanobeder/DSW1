package br.ufscar.dc.dsw.domain;

@SuppressWarnings("serial")
public class Cartao extends AbstractEntity<Long> {

	private String titular;

	private String CPF;
	
	private String numero;
	
	private String vencimento;
	
	private String CVV;
	
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

	public void setCVV(String cvv) {
		this.CVV = cvv;
	}

	@Override
	public String toString() {
		return numero + "/" + titular;
	}
}
