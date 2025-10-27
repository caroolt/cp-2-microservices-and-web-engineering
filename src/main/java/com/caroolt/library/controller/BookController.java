package com.caroolt.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caroolt.library.dto.BookRequestCreate;
import com.caroolt.library.dto.BookRequestUpdate;
import com.caroolt.library.dto.BookResponse;
import com.caroolt.library.service.BookService;

/**
 * Controller responsável por gerenciar operações relacionadas aos livros
 * do acervo da biblioteca.
 */
@RestController
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;

    /**
     * Adiciona um novo livro ao acervo
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequestCreate bookRequest) {
        var book = bookService.createBook(bookRequest);
        var response = new BookResponse().toDto(book);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Atualiza informações de um livro existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequestUpdate bookRequest) {
        return bookService.updateBook(id, bookRequest)
            .map(book -> new BookResponse().toDto(book))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um livro do acervo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = bookService.deleteBook(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Busca um livro específico pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
            .map(book -> new BookResponse().toDto(book))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os livros disponíveis no acervo
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAll()
            .stream()
            .map(book -> new BookResponse().toDto(book))
            .toList();

        return ResponseEntity.ok(books);
    }
}
