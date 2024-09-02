package com.gonchaba.libraryapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gonchaba.libraryapplication.dto.BookDTO;
import com.gonchaba.libraryapplication.model.Book;
import com.gonchaba.libraryapplication.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Mstahiki meya");
        book.setAuthor("Kevin wanjiru");
        book.setYear(2013);

        bookDTO = new BookDTO();
        bookDTO.setTitle("Kifo kisimani");
        bookDTO.setAuthor("Ken walibora");
        bookDTO.setYear(2024);
    }

    @Test
    void createBook_ReturnsCreatedBook() throws Exception {
        Mockito.when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    void getBookById_ReturnsBook() throws Exception {
        Mockito.when(bookService.findBookById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    void getBookById_ReturnsNotFound() throws Exception {
        Mockito.when(bookService.findBookById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllBooks_ReturnsListOfBooks() throws Exception {
        List<Book> books = List.of(book);
        Mockito.when(bookService.findAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value(book.getTitle()));
    }

    @Test
    void updateBook_ReturnsUpdatedBook() throws Exception {
        Mockito.when(bookService.updateBook(any(BookDTO.class), anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(put("/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("$.author").value(bookDTO.getAuthor()));
    }

    @Test
    void updateBook_ReturnsNotFound() throws Exception {
        Mockito.when(bookService.updateBook(any(BookDTO.class), anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBook_ReturnsNoContent() throws Exception {
        Mockito.doNothing().when(bookService).deleteBook(anyLong());

        mockMvc.perform(delete("/books/{id}", book.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBook_ReturnsInternalServerError() throws Exception {
        Mockito.doThrow(new RuntimeException("Error deleting book")).when(bookService).deleteBook(anyLong());

        mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }
}
