package com.caroolt.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caroolt.library.dto.AuthorRequestCreate;
import com.caroolt.library.dto.AuthorRequestUpdate;
import com.caroolt.library.model.Author;
import com.caroolt.library.repository.AuthorRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada aos autores
 */
@Service
public class AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Cria um novo autor no sistema
     */
    public Author createAuthor(AuthorRequestCreate authorRequest) {
        Author newAuthor = authorRequest.toModel();
        return authorRepository.save(newAuthor);
    }

    /**
     * Atualiza informações de um autor existente
     */
    public Optional<Author> updateAuthor(Long id, AuthorRequestUpdate authorRequest) {
        return authorRepository.findById(id)
            .map(existingAuthor -> authorRepository.save(authorRequest.toModel(existingAuthor)));
    }

    /**
     * Remove um autor do sistema
     */
    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Busca um autor pelo ID
     */
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    /**
     * Retorna todos os autores cadastrados
     */
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
