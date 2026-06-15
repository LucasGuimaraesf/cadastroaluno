package com.example.cadastroaluno.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity //Transforma essa classe numa tabela do banco de dados. Cada atributo vira uma coluna.
public class Aluno {

    /*
    Sumário

    @ID

    @NotBlank ->Verifica se está em branco

    @Size -> Define o tamanho do texto entre min e max (x,y)

    @Pattern (regexp = "\\d+") -> Recuso se o tipo for diferente. No java, escrevemos \\d porque a barra \ precisa ser "escapada".
        \\d -> significa um dígito (0 a 9)
        + -> Significa "um ou mais"
        Juntando \\d+ = "um ou mais dígitos, e nada além disso" 
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Chave primaria gerada automaticamente (1,2,3,...)

    @NotBlank(message = "O nome é obrigatório") //Verifica se está em branco
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")//Define o tamanho do texto entre min e max (x,y)
    private String nome;

    @NotBlank(message = "A matrícula é obrigatória")
    @Pattern(regexp = "\\d+", message = "A matrícula deve conter apenas números") //O texto tem que casar com um padrão
    private String matricula;

    @NotBlank(message = "O curso é obrigatório")
    private String curso;

    @NotBlank(message = "O período é obrigatório")
    @Pattern(regexp = "\\d+", message = "O período deve conter apenas números")
    private String periodo;

    public Aluno() {}

    public Aluno(String nome, String matricula, String curso, String periodo) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.periodo = periodo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
}