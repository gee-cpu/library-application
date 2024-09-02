package com.gonchaba.libraryapplication.repository;

import com.gonchaba.libraryapplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}