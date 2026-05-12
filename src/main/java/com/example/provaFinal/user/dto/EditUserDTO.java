package com.example.provaFinal.user.dto;

import com.example.provaFinal.user.Papel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserDTO {

    private String nome;
    private String cpf;
    private Papel papel;

}

