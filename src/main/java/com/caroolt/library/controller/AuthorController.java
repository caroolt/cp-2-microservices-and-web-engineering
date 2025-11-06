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

import com.caroolt.library.dto.AuthorRequestCreate;
import com.caroolt.library.dto.AuthorRequestUpdate;
import com.caroolt.library.dto.AuthorResponse;
import com.caroolt.library.service.AuthorService;

/**
 * Controller responsável por gerenciar operações relacionadas aos autores
 * da biblioteca digital.
 */
@RestController
@RequestMapping("/api/${api.version}/authors")
public class AuthorController {
    
    @Autowired
    private AuthorService authorService;

    /**
     * Cria um novo autor no sistema
     */
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorRequestCreate authorRequest) {
        var author = authorService.createAuthor(authorRequest);
        var response = new AuthorResponse().toDto(author);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Atualiza informações de um autor existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequestUpdate authorRequest) {
        return authorService.updateAuthor(id, authorRequest)
            .map(author -> new AuthorResponse().toDto(author))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um autor do sistema
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        boolean deleted = authorService.deleteAuthor(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Busca um autor específico pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id)
            .map(author -> new AuthorResponse().toDto(author))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os autores cadastrados no sistema
     */
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        List<AuthorResponse> authors = authorService.getAll()
            .stream()
            .map(author -> new AuthorResponse().toDto(author))
            .toList();

        return ResponseEntity.ok(authors);
    }
}
