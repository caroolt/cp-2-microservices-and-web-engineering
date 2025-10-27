package com.caroolt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caroolt.library.model.Loan;

public interface LoanRepository extends JpaRepository <Loan, Long>{

}
