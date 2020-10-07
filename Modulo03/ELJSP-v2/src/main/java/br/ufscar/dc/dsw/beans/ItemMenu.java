package br.ufscar.dc.dsw.beans;

public class ItemMenu {
    private String nome;
    private String link;

    public ItemMenu(String nome, String link) {
        this.nome = nome;
        this.link = link;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
