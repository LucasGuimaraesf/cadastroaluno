package com.example.cadastroaluno.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice = "conselheiro" global. Centraliza o tratamento de exceções
// de TODOS os controllers num único lugar, evitando try/catch espalhado pelo código.
@ControllerAdvice // "Departamento de reclamações da empresa" -> Se algo der errado ele passa por aqui e decide como responder
public class GlobalExceptionHandler {

    // Captura especificamente a nossa exceção personalizada e mostra a página de erro.
    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public String handleAlunoNaoEncontrado(AlunoNaoEncontradoException ex, Model model) {
        model.addAttribute("mensagemErro", ex.getMessage());
        return "erro";
    }

    // Rede de segurança: captura qualquer outro erro inesperado.
    @ExceptionHandler(Exception.class)
    public String handleErroGeral(Exception ex, Model model) {
        model.addAttribute("mensagemErro", "Ocorreu um erro inesperado: " + ex.getMessage());
        return "erro";
    }
}
