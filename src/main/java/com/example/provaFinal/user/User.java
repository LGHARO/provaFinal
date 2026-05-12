package com.example.provaFinal.user;

import com.example.provaFinal.curso.Curso;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity

@Table(name = "usuario")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false, unique = true)
        private String cpf;

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private Papel papel;

        @ManyToMany(mappedBy = "users")
        private Set<Curso> cursos = new HashSet<>();





}
