package com.example.java_code_learn_jdbc_template.service.impl;

import com.example.java_code_learn_jdbc_template.dto.BookDto;
import com.example.java_code_learn_jdbc_template.model.Book;
import com.example.java_code_learn_jdbc_template.repository.BookRepository;
import com.example.java_code_learn_jdbc_template.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Override
    public Book save(BookDto dto) {
        if (repository.findByDto(dto).isPresent()) {
            throw new RuntimeException("Already exist");
        } else {
            return repository.saveBook(dto);
        }
    }

    @Override
    public Book update(Long id, BookDto dto) {
        if (repository.findById(id).isPresent()) {
            throw new RuntimeException("Book is not exist");
        } else {
            return repository.updateBook(id, dto);
        }
    }

    @Override
    public Book get(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Book is not exist"));
    }

    @Override
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new RuntimeException("Book is not exist");
        } else {
            repository.deleteBook(id);
        }
    }
}
