package com.example.provaFinal.user;

import com.example.provaFinal.user.dto.EditUserDTO;
import com.example.provaFinal.user.dto.ResponseUserDTO;
import com.example.provaFinal.user.dto.SaveUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseUserDTO save(SaveUserDTO saveUserDTO, Integer userId) {

        // valida tipo de user
        validarAdmin(userId);

        // se o usuario ja existir ele nn é adicionado novamente
        if (userRepository.existsByCpf(saveUserDTO.getCpf())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CPF já cadastrado"
            );
        }

        User user = new User();
        user.setNome(saveUserDTO.getNome());
        user.setCpf(saveUserDTO.getCpf());
        user.setPapel(saveUserDTO.getPapel());

        user = userRepository.save(user);
        return ResponseUserDTO.toDTO(user);
    }

    public User get(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user não encontrado"
                ));
    }

    public ResponseUserDTO getDTO(Integer id) {
        return ResponseUserDTO.toDTO(get(id));
    }

    public Page<ResponseUserDTO> list(String nome, Pageable pageable) {
        if (nome != null) {
            return userRepository
                    .findByNomeContaining(nome, pageable)
                    .map(ResponseUserDTO::toDTO);
        }

        return userRepository
                .findAll(pageable)
                .map(ResponseUserDTO::toDTO);
    }

    public ResponseUserDTO edit(Integer id, EditUserDTO editUserDTO, Integer userId) {
        validarAdmin(userId);

        User userDB = get(id);

        userDB.setNome(editUserDTO.getNome());
        userDB.setCpf(editUserDTO.getCpf());
        userDB.setPapel(editUserDTO.getPapel());

        userDB = userRepository.save(userDB);
        return ResponseUserDTO.toDTO(userDB);
    }

    public void delete(Integer id, Integer userId) {
        validarAdmin(userId);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user não encontrado"
                ));

        userRepository.delete(user);
    }

    private User validarAdmin(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user não encontrado"
                ));

        if (user.getPapel() != Papel.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "voce não é admin"
            );
        }

        return user;
    }
}