
package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.ufscar.dc.dsw.domain.enumeration.Categoria;
import br.ufscar.dc.dsw.domain.enumeration.Status;

@SuppressWarnings("serial")
@Entity
@Table(name = "Transacao")
public class Transacao extends AbstractEntity<Long> {

	@NotBlank
	@Column(nullable = false, length = 60)
	private String descricao;
	
	@NotNull
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;
	
	@NotBlank
	@Column(nullable = false, length = 10)
	private String data;

	@NotNull
	@Column(nullable = false, length = 9)
	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	@NotNull
	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cartao_id")
	private Cartao cartao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria tipo) {
		this.categoria = tipo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
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