package com.caroolt.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caroolt.library.dto.LoanRequestCreate;
import com.caroolt.library.dto.LoanRequestUpdate;
import com.caroolt.library.model.Book;
import com.caroolt.library.model.Loan;
import com.caroolt.library.repository.BookRepository;
import com.caroolt.library.repository.LoanRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada aos empréstimos
 */
@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private BookRepository bookRepository;

    /**
     * Registra um novo empréstimo de livro
     */
    public Loan createLoan(LoanRequestCreate loanRequest) {
        Book book = bookRepository.findById(loanRequest.getBookId())
            .orElseThrow(() -> new RuntimeException(
                "Livro inexistente - ID: " + loanRequest.getBookId()));

        Loan newLoan = loanRequest.toModel(book);
        return loanRepository.save(newLoan);
    }

    /**
     * Remove um empréstimo do sistema
     */
    public boolean deleteLoan(Long id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Atualiza informações de um empréstimo existente
     */
    public Optional<Loan> updateLoan(Long id, LoanRequestUpdate loanRequest) {
        return loanRepository.findById(id)
            .map(existingLoan -> {
                Book book = bookRepository.findById(loanRequest.getBookId())
                    .orElseThrow(() -> new RuntimeException("Livro inexistente! ID: " + loanRequest.getBookId()));
                return loanRepository.save(loanRequest.toModel(existingLoan, book));
            });
    }

    /**
     * Busca um empréstimo pelo ID
     */
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    /**
     * Retorna todos os empréstimos registrados
     */
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }
}
