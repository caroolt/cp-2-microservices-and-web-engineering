package com.caroolt.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caroolt.library.dto.BookRequestCreate;
import com.caroolt.library.dto.BookRequestUpdate;
import com.caroolt.library.model.Author;
import com.caroolt.library.model.Book;
import com.caroolt.library.repository.AuthorRepository;
import com.caroolt.library.repository.BookRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada aos livros
 */
@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Cria um novo livro no acervo
     */
    public Book createBook(BookRequestCreate bookRequest) {
        Author author = authorRepository.findById(bookRequest.getAuthorId())
            .orElseThrow(() -> new RuntimeException(
                "Autor inexistente - ID: " + bookRequest.getAuthorId()));
        
        Book newBook = bookRequest.toModel(author);
        return bookRepository.save(newBook);        
    }

    /**
     * Atualiza informações de um livro existente
     */
    public Optional<Book> updateBook(Long id, BookRequestUpdate bookRequest) {
        return bookRepository.findById(id)
            .map(existingBook -> {
                Author author = authorRepository.findById(bookRequest.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Autor inexistente!"));
                return bookRepository.save(bookRequest.toModel(existingBook, author));
            });
    }

    /**
     * Remove um livro do acervo
     */
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Busca um livro pelo ID
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Retorna todos os livros do acervo
     */
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
