package com.example.provaFinal.curso;

import com.example.provaFinal.curso.dto.EditCursoDTO;
import com.example.provaFinal.curso.dto.ResponseCursoDTO;
import com.example.provaFinal.curso.dto.SaveCursoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // post do curso
    @PostMapping
    public ResponseCursoDTO saveCurso(@Valid @RequestBody SaveCursoDTO curso, @RequestHeader ("X-USER-ID") Integer userId) {
        return cursoService.save(curso, userId);
    }

    // get do curso
    @GetMapping("/{id}")
    public ResponseCursoDTO getCurso(@PathVariable Integer id) {
        return cursoService.getDTO(id);
    }

    // pega todos os cursos
    @GetMapping
    public Page<ResponseCursoDTO> listCursos(
            @RequestParam(required = false, name = "nome") String nome,
            Pageable pageable) {
        return cursoService.list(nome, pageable);
    }

    // put do curso
    @PutMapping("/{id}")
    public ResponseCursoDTO editCurso(@PathVariable Integer id, @RequestBody EditCursoDTO curso, @RequestHeader ("X-USER-ID") Integer userId) {
        return cursoService.edit(id, curso, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteCurso(
            @PathVariable Integer id,
            @RequestHeader("X-USER-ID") Integer userId) {
        cursoService.delete(id, userId);
    }


    @PostMapping("/{cursoId}/users/{userId}")
    public ResponseCursoDTO addUserToCurso(@PathVariable Integer cursoId,
                                           @PathVariable Integer userId,
                                           @RequestHeader("X-USER-ID") Integer adminId) {
        return cursoService.addUserToCurso(cursoId, userId, adminId);
    }

    @DeleteMapping("/{cursoId}/users/{userId}")
    public ResponseCursoDTO removeUserFromCurso(@PathVariable Integer cursoId,
                                                @PathVariable Integer userId,
                                                @RequestHeader("X-USER-ID") Integer adminId) {
        return cursoService.removeUserFromCurso(cursoId, userId, adminId);
    }




}