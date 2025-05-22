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
				.publishDate(LocalDate.of(2003, 06, 14))
				.build();

		Book savedBook = bookRepository.save(book);
		assertThat(savedBook.getId()).isNotNull();
	}

	@Test
	void testCreateBook2(){
		Book book = Book.builder()
				.title("2. kitap")
				.author("a")
				.genre("g")
				.publishDate(LocalDate.of(2002, 04, 12))
				.build();

		Book savedBook = bookRepository.save(book);
		assertThat(savedBook.getId()).isNotNull();

	}

	@Test
	void testDelete(){
		Book book = Book.builder()
				.title("silcez")
				.author("a")
				.genre("g")
				.publishDate(LocalDate.of(2003, 06, 14))
				.build();
		Book savedBook = bookRepository.save(book);

		assertThat(savedBook.getId()).isNotNull();


		Long bookID = savedBook.getId();
		bookRepository.deleteById(bookID);

		assertThat(bookRepository.findById(bookID)).isNotPresent();

	}


	@Test
	void testUpdate(){
		Book book = Book.builder()
				.title("updatelenmemiş hali")
				.author("a")
				.genre("g")
				.publishDate(LocalDate.of(2003, 06, 14))
				.build();
		Book savedBook = bookRepository.save(book);
		long bookID = savedBook.getId();
		savedBook.setTitle("güncellenmiş hali");
		savedBook.setAuthor("yeni author");
		savedBook.setGenre("yeni genre");

		Book updated = bookRepository.save(savedBook);
		assertThat(updated.getId()).isEqualTo(bookID);
		assertThat(updated.getTitle()).isEqualTo("güncellenmiş hali");






	}


}
