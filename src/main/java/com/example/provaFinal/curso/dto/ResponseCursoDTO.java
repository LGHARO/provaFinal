package com.example.provaFinal.curso.dto;

import com.example.provaFinal.curso.Curso;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseCursoDTO {
    private String nome;
    private String descricao;


    public static @NonNull ResponseCursoDTO toDTO(Curso curso) {
        ResponseCursoDTO responseCursoDTO = new ResponseCursoDTO();
        // troca nome
        responseCursoDTO.setNome(curso.getNome());
        // troca descricao
        responseCursoDTO.setDescricao(curso.getDescricao());
        return responseCursoDTO;
    }
}
