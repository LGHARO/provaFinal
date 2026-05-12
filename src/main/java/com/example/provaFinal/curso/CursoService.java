package com.example.provaFinal.curso;

import com.example.provaFinal.curso.dto.EditCursoDTO;
import com.example.provaFinal.curso.dto.ResponseCursoDTO;
import com.example.provaFinal.curso.dto.SaveCursoDTO;
import com.example.provaFinal.curso.exception.CursoNotFoundException;
import com.example.provaFinal.user.Papel;
import com.example.provaFinal.user.User;
import com.example.provaFinal.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseCursoDTO save(SaveCursoDTO saveCursoDTO, Integer userId) {
        validarAdmin(userId);

        Curso curso = new Curso();
        curso.setNome(saveCursoDTO.getNome());
        curso.setDescricao(saveCursoDTO.getDescricao());

        curso = cursoRepository.save(curso);
        return ResponseCursoDTO.toDTO(curso);
    }

    public Curso get(Integer id) {
        return cursoRepository.findById(id)
                .orElseThrow(CursoNotFoundException::new);
    }

    public ResponseCursoDTO getDTO(Integer id) {
        return ResponseCursoDTO.toDTO(get(id));
    }

    public Page<ResponseCursoDTO> list(String nome, Pageable pageable) {
        if (nome != null) {
            return cursoRepository.findByNomeContaining(nome, pageable)
                    .map(ResponseCursoDTO::toDTO);
        }

        return cursoRepository.findAll(pageable)
                .map(ResponseCursoDTO::toDTO);
    }

    public ResponseCursoDTO edit(Integer id, EditCursoDTO editCursoDTO, Integer userId) {
        validarAdmin(userId);

        Curso cursoDB = get(id);
        cursoDB.setNome(editCursoDTO.getNome());
        cursoDB.setDescricao(editCursoDTO.getDescricao());

        cursoDB = cursoRepository.save(cursoDB);
        return ResponseCursoDTO.toDTO(cursoDB);
    }

    public void delete(Integer id, Integer userId) {
        validarAdmin(userId);

        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "curso não encontrado"
                ));

        cursoRepository.delete(curso);
    }

    @Transactional
    public ResponseCursoDTO addUserToCurso(Integer cursoId, Integer userId, Integer adminId) {
        validarAdmin(adminId);

        Curso curso = get(cursoId);
        User user = getUser(userId);

        if (curso.getUsers().contains(user)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "usuário já está nesse curso"
            );
        }

        curso.getUsers().add(user);
        user.getCursos().add(curso);

        cursoRepository.save(curso);
        return ResponseCursoDTO.toDTO(curso);
    }


    public ResponseCursoDTO removeUserFromCurso(Integer cursoId, Integer userId, Integer adminId) {
        validarAdmin(adminId);

        Curso curso = get(cursoId);
        User user = getUser(userId);

        if (!curso.getUsers().remove(user)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "usuário não está nesse curso"
            );
        }

        user.getCursos().remove(curso);

        cursoRepository.save(curso);
        return ResponseCursoDTO.toDTO(curso);
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "user não encontrado"
                ));
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