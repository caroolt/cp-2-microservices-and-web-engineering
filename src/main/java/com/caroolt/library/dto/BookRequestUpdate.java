package com.caroolt.library.dto;

import com.caroolt.library.model.Author;
import com.caroolt.library.model.Book;

/**
 * DTO para atualização de livros existentes no acervo
 */
public class BookRequestUpdate {
    private String title;
    private String description;
    private Long authorId;

    /**
     * Atualiza um modelo Book existente com os dados deste DTO
     * 
     * @param existingBook livro existente a ser atualizado
     * @param author autor associado ao livro
     * @return Book atualizado
     */
    public Book toModel(Book existingBook, Author author) {
        existingBook.setTitle(title);
        existingBook.setDescription(description);
        existingBook.setAuthor(author);
        return existingBook;
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
