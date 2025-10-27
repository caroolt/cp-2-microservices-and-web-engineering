package com.caroolt.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.caroolt.library.dto.BookRequestCreate;
import com.caroolt.library.model.Author;
import com.caroolt.library.model.Book;
import com.caroolt.library.repository.AuthorRepository;
import com.caroolt.library.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("J.K. Rowling");

        book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor(author);
    }

    @Test
    void createBook_Success() {
        BookRequestCreate dto = new BookRequestCreate();
        dto.setTitle("Harry Potter");
        dto.setAuthorId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.createBook(dto);

        assertNotNull(result);
        assertEquals("Harry Potter", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_AuthorNotFound() {
        BookRequestCreate dto = new BookRequestCreate();
        dto.setTitle("Harry Potter");
        dto.setAuthorId(2L);

        when(authorRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.createBook(dto));
        assertEquals("Autor inexistente - ID: 2", exception.getMessage());
    }

    @Test
    void getBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("Harry Potter", result.get().getTitle());
    }

    @Test
    void getAllBooks_Success() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> result = bookService.getAll();

        assertEquals(1, result.size());
        assertEquals("Harry Potter", result.get(0).getTitle());
    }

    @Test
    void deleteBook_Success() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        Boolean result = bookService.deleteBook(1L);

        assertTrue(result);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_NotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        Boolean result = bookService.deleteBook(1L);

        assertFalse(result);
        verify(bookRepository, never()).deleteById(1L);
    }
}
