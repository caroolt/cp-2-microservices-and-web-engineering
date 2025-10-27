package com.caroolt.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.caroolt.library.dto.AuthorRequestCreate;
import com.caroolt.library.dto.AuthorRequestUpdate;
import com.caroolt.library.model.Author;
import com.caroolt.library.repository.AuthorRepository;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarAutorCorretamente() {
        AuthorRequestCreate dto = mock(AuthorRequestCreate.class);
        Author autor = new Author();
        autor.setId(1L);
        when(dto.toModel()).thenReturn(autor);
        when(authorRepository.save(autor)).thenReturn(autor);

        Author resultado = authorService.createAuthor(dto);

        assertEquals(1L, resultado.getId());
        verify(authorRepository, times(1)).save(autor);
    }

    @Test
    void deveAtualizarAutorQuandoExistir() {
        Long id = 1L;
        Author existente = new Author();
        existente.setId(id);

        AuthorRequestUpdate dto = mock(AuthorRequestUpdate.class);
        Author atualizado = new Author();
        atualizado.setId(id);

        when(authorRepository.findById(id)).thenReturn(Optional.of(existente));
        when(dto.toModel(existente)).thenReturn(atualizado);
        when(authorRepository.save(atualizado)).thenReturn(atualizado);

        Optional<Author> resultado = authorService.updateAuthor(id, dto);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(authorRepository).save(atualizado);
    }

    @Test
    void naoDeveAtualizarAutorQuandoNaoExistir() {
        Long id = 99L;
        AuthorRequestUpdate dto = mock(AuthorRequestUpdate.class);
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Author> resultado = authorService.updateAuthor(id, dto);

        assertFalse(resultado.isPresent());
        verify(authorRepository, never()).save(any());
    }

    @Test
    void deveDeletarAutorQuandoExistir() {
        Long id = 1L;
        when(authorRepository.existsById(id)).thenReturn(true);

        Boolean resultado = authorService.deleteAuthor(id);

        assertTrue(resultado);
        verify(authorRepository).deleteById(id);
    }

    @Test
    void naoDeveDeletarAutorQuandoNaoExistir() {
        Long id = 1L;
        when(authorRepository.existsById(id)).thenReturn(false);

        Boolean resultado = authorService.deleteAuthor(id);

        assertFalse(resultado);
        verify(authorRepository, never()).deleteById(any());
    }

    @Test
    void deveBuscarAutorPorId() {
        Long id = 1L;
        Author autor = new Author();
        autor.setId(id);
        when(authorRepository.findById(id)).thenReturn(Optional.of(autor));

        Optional<Author> resultado = authorService.getAuthorById(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(authorRepository).findById(id);
    }

    @Test
    void deveListarTodosOsAutores() {
        List<Author> autores = List.of(new Author(), new Author());
        when(authorRepository.findAll()).thenReturn(autores);

        List<Author> resultado = authorService.getAll();

        assertEquals(2, resultado.size());
        verify(authorRepository).findAll();
    }
}