package com.gonchaba.libraryapplication.service;

import com.gonchaba.libraryapplication.dto.BookDTO;
import com.gonchaba.libraryapplication.model.Book;
import com.gonchaba.libraryapplication.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindBookByIdSuccess() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findBookById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testFindBookByIdFailure() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.findBookById(id);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setTitle("Test Title");
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.createBook(book);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookSuccess() {
        Long id = 1L;
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Updated Title");
        bookDTO.setAuthor("Updated Author");
        bookDTO.setYear(2022);

        Book book = new Book();
        book.setId(id);
        book.setTitle("Old Title");
        book.setAuthor("Old Author");
        book.setYear(2000);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Optional<Book> result = bookService.updateBook(bookDTO, id);

        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        assertEquals("Updated Author", result.get().getAuthor());
        assertEquals(2022, result.get().getYear());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateBookFailure() {
        Long id = 1L;
        BookDTO bookDTO = new BookDTO();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.updateBook(bookDTO, id);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        Long id = 1L;

        doNothing().when(bookRepository).deleteById(id);

        bookService.deleteBook(id);

        verify(bookRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAllBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }
}
