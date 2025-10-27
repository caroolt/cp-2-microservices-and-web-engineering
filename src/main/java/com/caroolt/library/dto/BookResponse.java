package com.caroolt.library.dto;

import com.caroolt.library.model.Book;

/**
 * DTO de resposta para dados de livros
 */
public class BookResponse {
    private Long id;
    private String title;
    private String description;
    private AuthorResponseWithoutBooks author;

    /**
     * Converte um modelo Book para este DTO de resposta
     * 
     * @param book modelo Book a ser convertido
     * @return BookResponse configurado com os dados do modelo
     */
    public BookResponse toDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.author = new AuthorResponseWithoutBooks().toDto(book.getAuthor());
        return this;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public AuthorResponseWithoutBooks getAuthor() {
        return author;
    }
    
    public void setAuthor(AuthorResponseWithoutBooks author) {
        this.author = author;
    }
}
