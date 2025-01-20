package com.example.java_code_learn_jdbc_template.controller;

import com.example.java_code_learn_jdbc_template.dto.BookDto;
import com.example.java_code_learn_jdbc_template.model.Book;
import com.example.java_code_learn_jdbc_template.service.impl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private BookServiceImpl service;

    private Book book1;
    private BookDto dto1;

    @BeforeEach
    void setUp() {
        book1 = new Book(1L, "Book title", "Book author",
                LocalDate.of(2024, 7, 15));
        dto1 = new BookDto("Dto title", "Dto author",
                LocalDate.of(2024, 7, 15));
    }

    @Test
    void getBookById() throws Exception{
        Mockito.when(service.get(Mockito.anyLong())).thenReturn(book1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value("Book title"));
    }

    @Test
    void createBook() throws Exception{
        String json = mapper.writeValueAsString(dto1);
        Mockito.when(service.save(Mockito.any(BookDto.class))).thenReturn(book1);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value("Book title"));
    }

    @Test
    void updateBook() throws Exception{
        String json = mapper.writeValueAsString(dto1);
        Mockito.when(service.update(Mockito.anyLong(), Mockito.any(BookDto.class)))
                .thenReturn(book1);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title")
                        .value("Book title"));
    }

    @Test
    void deleteBook() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}