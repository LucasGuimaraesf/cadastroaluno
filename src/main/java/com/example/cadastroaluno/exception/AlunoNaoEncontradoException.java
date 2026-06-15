package com.example.cadastroaluno.exception;

// Exceção personalizada lançada quando tentamos acessar/excluir um aluno que não existe.
// Estende RuntimeException (exceção "não checada"), por isso não precisa de try/catch no controller.
public class AlunoNaoEncontradoException extends RuntimeException {

    public AlunoNaoEncontradoException(Long id) {
        super("Aluno não encontrado com o ID: " + id);
    }
}
