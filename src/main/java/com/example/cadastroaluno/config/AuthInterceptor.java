package com.example.cadastroaluno.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.cadastroaluno.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Um "interceptor" roda ANTES de cada requisição chegar ao controller.
// É o nosso "segurança" conferindo a pulseira em toda página.
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        String uri = request.getRequestURI();

        // 1) Não está logado (sem pulseira)? Volta para o login.
        if (usuario == null) {
            response.sendRedirect("/login");
            return false; // interrompe: a requisição NÃO continua
        }

        // 2) Está logado, mas a rota é exclusiva de ADMIN e ele não é admin? Bloqueia.
        boolean rotaDeAdmin = uri.startsWith("/editar") || uri.startsWith("/excluir");
        if (rotaDeAdmin && !usuario.getPerfil().equals("ADMIN")) {
            response.sendRedirect("/?erroPermissao");
            return false;
        }

        // 3) Tudo certo: deixa a requisição seguir para o controller.
        return true;
    }
}
