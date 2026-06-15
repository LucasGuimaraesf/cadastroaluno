package com.example.cadastroaluno.model;

// POJO simples para representar um usuário do sistema.
// (NÃO tem @Entity: não é uma tabela do banco, é só um objeto na memória.)

public class Usuario {
    private String login;
    private String senha;
    private String perfil; //"ADMIN" ou "COLABORADOR"

    public Usuario(String login, String senha, String perfil){
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getPerfil() { return perfil; }
    
}