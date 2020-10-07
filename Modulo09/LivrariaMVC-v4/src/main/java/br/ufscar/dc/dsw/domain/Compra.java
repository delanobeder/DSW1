package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "Compra")
public class Compra extends AbstractEntity<Long> {
    
	@Column(columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;
    
	@NotNull(message = "{NotNull.compra.livro}")
	@ManyToOne
	@JoinColumn(name = "livro_id")
	private Livro livro;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@Column(nullable = false, name = "transacao_id")
	private Long transacaoID;

	@Transient
	private Cartao cartao;
	
	@Transient
	private String detalhes;
	
	@Transient
	private String data;
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal preco) {
		this.valor = preco;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Long getTransacaoID() {
		return transacaoID;
	}

	public void setTransacaoID(Long transacao) {
		this.transacaoID = transacao;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String descricao) {
		this.detalhes = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
