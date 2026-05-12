package com.example.provaFinal.user;

import com.example.provaFinal.user.dto.EditUserDTO;
import com.example.provaFinal.user.dto.ResponseUserDTO;
import com.example.provaFinal.user.dto.SaveUserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseUserDTO saveUser(
            @Valid @RequestBody SaveUserDTO user,
            @RequestHeader("X-USER-ID") Integer userId) {
        return userService.save(user, userId);
    }

    @GetMapping("/{id}")
    public ResponseUserDTO getUser(@PathVariable Integer id) {
        return userService.getDTO(id);
    }

    @GetMapping
    public Page<ResponseUserDTO> listUsers(
            @RequestParam(required = false, name = "nome") String nome,
            Pageable pageable) {
        return userService.list(nome, pageable);
    }

    @PutMapping("/{id}")
    public ResponseUserDTO editUser(
            @PathVariable Integer id,
            @RequestBody EditUserDTO user,
            @RequestHeader("X-USER-ID") Integer userId) {
        return userService.edit(id, user, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Integer id,
            @RequestHeader("X-USER-ID") Integer userId) {
        userService.delete(id, userId);
    }
}