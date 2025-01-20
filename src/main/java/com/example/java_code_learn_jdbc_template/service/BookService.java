package com.example.java_code_learn_jdbc_template.service;

import com.example.java_code_learn_jdbc_template.dto.BookDto;
import com.example.java_code_learn_jdbc_template.model.Book;

public interface BookService {
    Book save(BookDto dto);

    Book update(Long id, BookDto dto);

    Book get(Long id);

    void delete(Long id);
}
