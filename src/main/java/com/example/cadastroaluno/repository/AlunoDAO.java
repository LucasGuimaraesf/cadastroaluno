package com.example.cadastroaluno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cadastroaluno.model.Aluno;

//É uma interface que estende JpaRepository. Isso é a "mágica" do String: nós não escrevemos o SQL
//Só por herdar de JpaRepository<Aluno, Long>, já ganhamos pronto:
//findAll() -> buscar todos os alunos
//save() -> salvar/atualizar
//deleteById() -> excluir
//Aluno = qual entidade ele gerencia; Lon = o tipo do id.
//DAO = Data Access Object, "objeto de acesso a dados".
public interface AlunoDAO extends JpaRepository<Aluno, Long> {
}