package com.caroolt.library.dto;

import java.time.LocalDate;
import java.util.List;

import com.caroolt.library.model.Author;

/**
 * DTO de resposta para dados de autores
 */
public class AuthorResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private List<BookResponse> books;

    /**
     * Converte um modelo Author para este DTO de resposta
     * 
     * @param author modelo Author a ser convertido
     * @return AuthorResponse configurado com os dados do modelo
     */
    public AuthorResponse toDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.birthDate = author.getBirthDate();
        this.books = author.getBooks()
            .stream()
            .map(book -> new BookResponse().toDto(book))
            .toList();
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
    
    public List<BookResponse> getBooks() {
        return books;
    }
    
    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }
}
