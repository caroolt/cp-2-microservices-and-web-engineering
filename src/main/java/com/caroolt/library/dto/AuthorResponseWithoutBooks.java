package com.caroolt.library.dto;

import java.time.LocalDate;

import com.caroolt.library.model.Author;

/**
 * DTO de resposta para dados de autores sem incluir a lista de livros
 * Utilizado para evitar referências circulares em respostas JSON
 */
public class AuthorResponseWithoutBooks {
    private Long id;
    private String name;
    private LocalDate birthDate;

    /**
     * Converte um modelo Author para este DTO de resposta simplificado
     * 
     * @param author modelo Author a ser convertido
     * @return AuthorResponseWithoutBooks configurado com os dados do modelo
     */
    public AuthorResponseWithoutBooks toDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.birthDate = author.getBirthDate();
        return this;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
