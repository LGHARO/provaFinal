package com.example.provaFinal.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByNomeContaining(String nome, Pageable pageable);

    boolean existsByCpf(String cpf);
}