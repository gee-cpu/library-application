package com.gonchaba.libraryapplication.service;

import com.gonchaba.libraryapplication.dto.BookDTO;
import com.gonchaba.libraryapplication.model.Book;
import com.gonchaba.libraryapplication.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Optional<Book> findBookById(Long id) {
        try {
            return bookRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error finding book by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> updateBook(BookDTO bookDTO, Long id) {
        try {
            return bookRepository.findById(id).map(book -> {
                book.setTitle(bookDTO.getTitle());
                book.setAuthor(bookDTO.getAuthor());
                book.setYear(bookDTO.getYear());
                return bookRepository.save(book);
            });
        } catch (Exception e) {
            System.err.println("Error updating book: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Book createBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            System.err.println("Error creating book: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    @Override
    public List<Book> findAllBooks() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error finding all books: " + e.getMessage());
            return List.of();
        }
    }
}
