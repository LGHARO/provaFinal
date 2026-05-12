package com.example.provaFinal.user.dto;

import com.example.provaFinal.user.Papel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SaveUserDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @NotNull
    private Papel papel;



}