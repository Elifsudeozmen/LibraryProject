package com.example.libraryProject;

import com.example.libraryProject.model.Book;
import com.example.libraryProject.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("mysql")
@SpringBootTest
class LibraryProjectApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Test
	void testCreateBook() {
		Book book = Book.builder()
				.title("deneme 1")
				.author("author")
				.genre("genre")
				.publishDate(LocalDate.of(2003,06,14))
				.build();

		Book savedBook = bookRepository.save(book);
		assertThat(savedBook.getId()).isNotNull();}
}
