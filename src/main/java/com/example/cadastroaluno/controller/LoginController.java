package com.example.cadastroaluno.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.cadastroaluno.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    // "Banco de usuarios fake" de usuários de teste, em memória (1 adm + 1 colaborador).
    private final List<Usuario> usuarios = List.of( //List.off -> cria uma lista fixa e imutavel.
        new Usuario("admin", "admin123", "ADMIN"),
        new Usuario("colab", "colab123", "COLABORADOR")
    );

    // 1 - Mostra a aba inicial (Tela de Login) -> Apenas mostrando a tela
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //  2 - Recebe o formulário de login (Usuário + senha digitados) -> Processa o que foi enviado
    @PostMapping("/login")
    public String autenticar(@RequestParam String login, @RequestParam String senha, HttpSession session, Model model) {

        //Procura um usuario que o login e senha batem com o que foi digitado
        Usuario usuarioEncontrado = null;
        for (Usuario u: usuarios){
            if (u.getLogin().equals(login) && u.getSenha().equals(senha)){
                usuarioEncontrado = u;
                break;
            }
        }

        //Não achou? Vai voltar para o login com uma mensagem de erro.
        if(usuarioEncontrado == null) {
            model.addAttribute("erro", "Login ou senha inválidos!");
            return "login";
        }

        //Se achar! Guarda o usuário na sessão e vai para a lista.
        session.setAttribute("usuarioLogado", usuarioEncontrado);
        return "redirect:/";
    }

    // 3 - Encerrando a sessão
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
