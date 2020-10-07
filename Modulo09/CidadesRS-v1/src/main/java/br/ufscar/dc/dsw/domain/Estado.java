
package br.ufscar.dc.dsw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Estado")
public class Estado extends AbstractEntity<Long>{
    
   @Column(nullable = false, length = 2)
   private String sigla;
	
   @Column(nullable = false, length = 30)
    private String nome;

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

    @Override
    public String toString() {
        return nome + " (" + sigla + ")";
    }
 }