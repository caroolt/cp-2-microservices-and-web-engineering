package com.caroolt.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caroolt.library.model.Author;

public interface AuthorRepository extends JpaRepository <Author, Long>{

}
