
package br.ufscar.dc.dsw.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@JsonIgnoreProperties(value = { "cidades" })
@Table(name = "Estado")
public class Estado extends AbstractEntity<Long>{
    
   @Column(nullable = false, length = 2)
   private String sigla;
	
   @Column(nullable = false, length = 30)
    private String nome;

    @OneToMany(mappedBy = "estado")
	private List<Cidade> cidades;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    @Override
    public String toString() {
        return nome + " (" + sigla + ")";
    }

    
 }