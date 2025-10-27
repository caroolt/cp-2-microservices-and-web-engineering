package com.caroolt.library.dto;

import com.caroolt.library.model.Author;
import com.caroolt.library.model.Book;

/**
 * DTO para criação de novos livros no acervo
 */
public class BookRequestCreate {
    private String title;
    private String description;
    private Long authorId;

    /**
     * Converte este DTO para um modelo Book
     * 
     * @param author autor associado ao livro
     * @return Book configurado com os dados do DTO
     */
    public Book toModel(Author author) {
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(author);
        return book;
    }

    // Getters e Setters
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

    public Long getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
