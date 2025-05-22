package com.example.libraryProject.controller;

import com.example.libraryProject.repository.BookRepository;
import com.example.libraryProject.repository.CategoryRepository;
import com.example.libraryProject.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/books")
@RestController


public class BookController {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;


    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book, @AuthenticationPrincipal UserDetails userDetails) {
        categoryRepository.findById(book.getCategory().getId()).ifPresent(book::setCategory);
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookData) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookData.getTitle());
            book.setAuthor(bookData.getAuthor());
            book.setGenre(bookData.getGenre());
            book.setPublishDate(bookData.getPublishDate());
            book.setCategory(bookData.getCategory());
            return ResponseEntity.ok(bookRepository.save(book));
        }).orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return  ResponseEntity.ok().build();
    }







}
