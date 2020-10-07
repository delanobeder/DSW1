package br.ufscar.dc.dsw.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Usuario {
    private String nomeLogin, nome, senha;
    private final Date ultimoAcesso;

    public Usuario() {
        ultimoAcesso = new Date();
    }

    public void setNomeLogin(final String nomeLogin) {
        this.nomeLogin = nomeLogin;
    }

    public String getNomeLogin() {
        return nomeLogin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(final String senha) {
        this.senha = senha;
    }

    public String getUltimoAcesso() {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss:SSS");
        return sdf.format(ultimoAcesso);
    }
}