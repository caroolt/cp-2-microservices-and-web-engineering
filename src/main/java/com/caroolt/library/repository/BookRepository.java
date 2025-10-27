package com.caroolt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caroolt.library.model.Book;

public interface BookRepository extends JpaRepository <Book, Long>{

}
