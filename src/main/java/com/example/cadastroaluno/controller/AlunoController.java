package com.example.cadastroaluno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cadastroaluno.exception.AlunoNaoEncontradoException;
import com.example.cadastroaluno.model.Aluno;
import com.example.cadastroaluno.repository.AlunoDAO;

import jakarta.validation.Valid;


@Controller
public class AlunoController {

    @Autowired //Autowired injeta o AlunoDAO para ele oider acessar o banco
    //Cada método responde a uma URL:
    private AlunoDAO alunoDAO;

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("alunos", alunoDAO.findAll());
        return "alunos";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Aluno aluno, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "form";
        }

        boolean novoCadastro = (aluno.getId() == null);   // 1. ele tem id? guardo a resposta
        alunoDAO.save(aluno);                              // 2. salva (insere OU atualiza)
        redirectAttributes.addFlashAttribute("msg", // 3. escolho a mensagem certa
                novoCadastro ? "Aluno cadastrado com sucesso!" : "Aluno atualizado com sucesso!");
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Aluno aluno = alunoDAO.findById(id)
            .orElseThrow(() -> new AlunoNaoEncontradoException(id));
        model.addAttribute("aluno", aluno);
        return "form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        // Se o aluno não existir, lança a exceção tratada pelo GlobalExceptionHandler
        if (!alunoDAO.existsById(id)) {
            throw new AlunoNaoEncontradoException(id); // Lança a exceção
        }
        alunoDAO.deleteById(id);
        return "redirect:/";
    }    
}