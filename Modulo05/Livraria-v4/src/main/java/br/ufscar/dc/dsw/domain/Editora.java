package br.ufscar.dc.dsw.domain;

public class Editora {

    private Long id;
    private String CNPJ;
    private String nome;
    private int qtdeLivros;

    public Editora(Long id) {
        this.id = id;
    }

    public Editora(String CNPJ, String nome) {
        this.CNPJ = CNPJ;
        this.nome = nome;
    }

    public Editora(Long id, String CNPJ, String nome) {
        this(CNPJ, nome);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdeLivros() {
        return qtdeLivros;
    }

    public void setQtdeLivros(int qtdeLivros) {
        this.qtdeLivros = qtdeLivros;
    }
}