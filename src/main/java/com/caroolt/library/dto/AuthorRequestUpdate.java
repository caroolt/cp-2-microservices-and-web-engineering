package com.caroolt.library.dto;

import java.time.LocalDate;

import com.caroolt.library.model.Author;

/**
 * DTO para atualização de autores existentes no sistema
 */
public class AuthorRequestUpdate {
    private String name;
    private LocalDate birthDate;

    /**
     * Atualiza um modelo Author existente com os dados deste DTO
     * 
     * @param existingAuthor autor existente a ser atualizado
     * @return Author atualizado
     */
    public Author toModel(Author existingAuthor) {
        existingAuthor.setName(name);
        existingAuthor.setBirthDate(birthDate);
        return existingAuthor;
    }

    // Getters e Setters
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
