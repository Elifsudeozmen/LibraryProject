package com.example.libraryProject.controller;

import com.example.libraryProject.model.User;
import com.example.libraryProject.repository.BookRepository;
import com.example.libraryProject.repository.CategoryRepository;
import com.example.libraryProject.model.Book;
import com.example.libraryProject.repository.UserRepository;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserRepository userRepository;


    @GetMapping
    @PermitAll
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book, @AuthenticationPrincipal UserDetails userDetails) {
        categoryRepository.findById(book.getCategory().getId()).ifPresent(book::setCategory);
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        book.setLastModifiedBy(user);
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookData, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("UserDetails: " + userDetails);
        System.out.println("Username: " + (userDetails != null ? userDetails.getUsername() : "null"));
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookData.getTitle());
            book.setAuthor(bookData.getAuthor());
            book.setGenre(bookData.getGenre());
            book.setPublishDate(bookData.getPublishDate());
            book.setCategory(bookData.getCategory());
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
            book.setLastModifiedBy(user);
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
