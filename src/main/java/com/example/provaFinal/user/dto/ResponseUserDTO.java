package com.example.provaFinal.user.dto;

import com.example.provaFinal.user.Papel;
import com.example.provaFinal.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDTO {

    private String nome;
    private String cpf;
    private Papel papel;


    public static ResponseUserDTO toDTO(User user) {
        ResponseUserDTO dto = new ResponseUserDTO();

        // troca o nome
        dto.setNome(user.getNome());
        // troca o cpf
        dto.setCpf(user.getCpf());
        // troac o papel
        dto.setPapel(user.getPapel());


        return dto;
    }
}
