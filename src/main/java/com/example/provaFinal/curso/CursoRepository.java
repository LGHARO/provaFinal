package com.example.provaFinal.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    Page<Curso> findByNomeContaining(String nome, Pageable pageable);

    List<Curso> findByNomeAndDescricao(String nome, String descricao);


    Optional<Curso> findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByNome(String nome);


}