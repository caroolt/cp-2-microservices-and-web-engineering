package com.caroolt.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.caroolt.library.dto.LoanRequestCreate;
import com.caroolt.library.dto.LoanRequestUpdate;
import com.caroolt.library.model.Book;
import com.caroolt.library.model.Loan;
import com.caroolt.library.repository.BookRepository;
import com.caroolt.library.repository.LoanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    private Book book;
    private Loan loan;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");

        loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);
    }

    @Test
    void createLoan_Success() {
        LoanRequestCreate dto = new LoanRequestCreate();
        dto.setBookId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.createLoan(dto);

        assertNotNull(result);
        assertEquals(1L, result.getBook().getId());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void createLoan_BookNotFound() {
        LoanRequestCreate dto = new LoanRequestCreate();
        dto.setBookId(2L);

        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> loanService.createLoan(dto));
        assertEquals("Livro inexistente - ID: 2", exception.getMessage());
    }

    @Test
    void getLoanById_Success() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        Optional<Loan> result = loanService.getLoanById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getBook().getId());
    }

    @Test
    void getAllLoans_Success() {
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan));

        List<Loan> result = loanService.getAll();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getBook().getId());
    }

    @Test
    void deleteLoan_Success() {
        when(loanRepository.existsById(1L)).thenReturn(true);

        Boolean result = loanService.deleteLoan(1L);

        assertTrue(result);
        verify(loanRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteLoan_NotFound() {
        when(loanRepository.existsById(1L)).thenReturn(false);

        Boolean result = loanService.deleteLoan(1L);

        assertFalse(result);
        verify(loanRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateLoan_Success() {
        LoanRequestUpdate dto = new LoanRequestUpdate();
        dto.setBookId(1L);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Optional<Loan> result = loanService.updateLoan(1L, dto);

        assertTrue(result.isPresent());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void updateLoan_BookNotFound() {
        LoanRequestUpdate dto = new LoanRequestUpdate();
        dto.setBookId(2L);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> loanService.updateLoan(1L, dto));
        assertEquals("Livro inexistente! ID: 2", exception.getMessage());
    }
}
