package com.caroolt.library.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import com.caroolt.library.model.Author;

/**
 * DTO para criação de novos autores no sistema
 */
public class AuthorRequestCreate {
    private String name;
    private LocalDate birthDate;

    /**
     * Converte este DTO para um modelo Author
     * 
     * @return Author configurado com os dados do DTO
     */
    public Author toModel() {
        Author author = new Author();
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setBooks(new ArrayList<>());
        return author;
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
