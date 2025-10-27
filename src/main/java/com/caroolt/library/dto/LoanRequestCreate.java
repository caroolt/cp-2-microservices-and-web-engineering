package com.caroolt.library.dto;

import java.time.LocalDate;

import com.caroolt.library.model.Book;
import com.caroolt.library.model.Loan;

/**
 * DTO para criação de novos empréstimos no sistema
 */
public class LoanRequestCreate {
    private String borrowerName;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private Long bookId;

    /**
     * Converte este DTO para um modelo Loan
     * 
     * @param book livro a ser emprestado
     * @return Loan configurado com os dados do DTO
     */
    public Loan toModel(Book book) {
        Loan loan = new Loan();
        loan.setBorrowerName(borrowerName);
        loan.setLoanDate(loanDate);
        loan.setReturnDate(returnDate);
        loan.setBook(book);
        return loan;
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
