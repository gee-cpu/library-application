package com.gonchaba.libraryapplication.service;

import com.gonchaba.libraryapplication.dto.BookDTO;
import com.gonchaba.libraryapplication.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> findBookById(Long id);
    Optional<Book> updateBook(BookDTO bookDTO,Long id);
    Book createBook(Book book);
    void deleteBook(Long id);
    List<Book> findAllBooks();
}
