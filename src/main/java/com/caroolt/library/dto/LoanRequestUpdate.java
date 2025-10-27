package com.caroolt.library.dto;

import java.time.LocalDate;

import com.caroolt.library.model.Book;
import com.caroolt.library.model.Loan;

/**
 * DTO para atualização de empréstimos existentes no sistema
 */
public class LoanRequestUpdate {
    private String borrowerName;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private Long bookId;

    /**
     * Atualiza um modelo Loan existente com os dados deste DTO
     * 
     * @param existingLoan empréstimo existente a ser atualizado
     * @param book livro associado ao empréstimo
     * @return Loan atualizado
     */
    public Loan toModel(Loan existingLoan, Book book) {
        existingLoan.setBorrowerName(borrowerName);
        existingLoan.setLoanDate(loanDate);
        existingLoan.setReturnDate(returnDate);
        existingLoan.setBook(book);
        return existingLoan;
    }

    // Getters e Setters
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

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
