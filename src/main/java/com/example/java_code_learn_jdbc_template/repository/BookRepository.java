package com.example.java_code_learn_jdbc_template.repository;

import com.example.java_code_learn_jdbc_template.dto.BookDto;
import com.example.java_code_learn_jdbc_template.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookMapper = new RowMapper<Book>() {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id"), rs.getString("title"),
                    rs.getString("author"),
                    rs.getDate("published_date").toLocalDate());
        }
    };

    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        return jdbcTemplate.query(sql, bookMapper, id).stream().findFirst();
    }

    public Optional<Book> findByDto(BookDto dto) {
        String sql = "SELECT * FROM book WHERE title = ? AND author = ? AND published_date = ?";
        return jdbcTemplate.query(sql, bookMapper, dto.getTitle(), dto.getAuthor(),
                dto.getPublishedDate()).stream().findFirst();
    }

    public Book saveBook(BookDto dto) {
        String sql = "INSERT INTO book (title, author, published_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, dto.getTitle(), dto.getAuthor(),
                dto.getPublishedDate());
        return findByDto(dto).get();
    }

    public Book updateBook(Long id, BookDto dto) {
        String sql = "UPDATE book SET title = ?, author = ?, published_date = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, dto.getTitle(), dto.getAuthor(),
                dto.getPublishedDate(), id);
        return findById(id).get();
    }

    public void deleteBook(Long id) {
        if (findById(id).isEmpty()) {
            throw new RuntimeException("Book is not exist");
        } else {
            String sql = "DElETE FROM book * WHERE id = ?";
            jdbcTemplate.update(sql, id);
        }
    }
}
