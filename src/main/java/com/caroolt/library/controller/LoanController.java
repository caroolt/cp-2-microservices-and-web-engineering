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

import com.caroolt.library.dto.LoanRequestCreate;
import com.caroolt.library.dto.LoanRequestUpdate;
import com.caroolt.library.dto.LoanResponse;
import com.caroolt.library.service.LoanService;

/**
 * Controller responsável por gerenciar operações relacionadas aos empréstimos
 * de livros da biblioteca.
 */
@RestController
@RequestMapping("/loans")
public class LoanController {
    
    @Autowired
    private LoanService loanService;

    /**
     * Registra um novo empréstimo de livro
     */
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@RequestBody LoanRequestCreate loanRequest) {
        var loan = loanService.createLoan(loanRequest);
        var response = new LoanResponse().toDto(loan);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Atualiza informações de um empréstimo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponse> updateLoan(@PathVariable Long id, @RequestBody LoanRequestUpdate loanRequest) {
        return loanService.updateLoan(id, loanRequest)
            .map(loan -> new LoanResponse().toDto(loan))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cancela um empréstimo registrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        boolean cancelled = loanService.deleteLoan(id);
        return cancelled ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * Busca um empréstimo específico pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id)
            .map(loan -> new LoanResponse().toDto(loan))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os empréstimos registrados no sistema
     */
    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        List<LoanResponse> loans = loanService.getAll()
            .stream()
            .map(loan -> new LoanResponse().toDto(loan))
            .toList();

        return ResponseEntity.ok(loans);
    }
}

