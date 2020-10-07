
package br.ufscar.dc.dsw.domain;

@SuppressWarnings("serial")
public class Transacao extends AbstractEntity<Long> {

	private String descricao;
	
	private Double valor;

	private String data;

	private String categoria;

	private String status;
	
	private Cartao cartao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String tipo) {
		this.categoria = tipo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	
	@Override
	public String toString() {
		return "[" + categoria + "/" + status + "] " + descricao + " - " + valor + " - " + data + " (" + cartao + ")";
	}
}