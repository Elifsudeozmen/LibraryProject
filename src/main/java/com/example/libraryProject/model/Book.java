package com.example.libraryProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private String author;
    private String genre;
    private LocalDate publishDate;

// tek bir user last modify edebilir
    //@ManyToOne
    //private User lastModifiedBy;
    private String lastModifiedByUsername;


//birden fazla kitap aynı categoride olabilir
//bir kitap sadece bir kategoride olabilir(?)
    @ManyToOne
    private Category category;

}
