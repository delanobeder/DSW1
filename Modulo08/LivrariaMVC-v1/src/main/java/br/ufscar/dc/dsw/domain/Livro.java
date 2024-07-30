package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Livro")
public class Livro extends AbstractEntity<Long> {

	@NotBlank(message = "{NotBlank.livro.titulo}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String titulo;

	@NotBlank(message = "{NotBlank.livro.autor}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String autor;
    
	@NotNull(message = "{NotNull.livro.ano}")
	@Column(nullable = false, length = 5)
	private Integer ano;
	
	@NotNull(message = "{NotNull.livro.preco}")
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal preco;
    
	@NotNull(message = "{NotNull.livro.editora}")
	@ManyToOne
	@JoinColumn(name = "editora_id")
	private Editora editora;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}
}
