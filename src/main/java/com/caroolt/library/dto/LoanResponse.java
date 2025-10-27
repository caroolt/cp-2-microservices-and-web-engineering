package com.caroolt.library.dto;

import java.time.LocalDate;

import com.caroolt.library.model.Loan;

/**
 * DTO de resposta para dados de empréstimos
 */
public class LoanResponse {
    private Long id;
    private String borrowerName;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private BookResponse book;

    /**
     * Converte um modelo Loan para este DTO de resposta
     * 
     * @param loan modelo Loan a ser convertido
     * @return LoanResponse configurado com os dados do modelo
     */
    public LoanResponse toDto(Loan loan) {
        this.id = loan.getId();
        this.borrowerName = loan.getBorrowerName();
        this.loanDate = loan.getLoanDate();
        this.returnDate = loan.getReturnDate();
        this.book = new BookResponse().toDto(loan.getBook());
        return this;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BookResponse getBook() {
        return book;
    }

    public void setBook(BookResponse bookResponse) {
        this.book = bookResponse;
    }
}
