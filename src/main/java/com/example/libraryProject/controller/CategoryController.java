package com.example.libraryProject.controller;

import com.example.libraryProject.model.Category;
import com.example.libraryProject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor

@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;


    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category data) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(data.getName());
            category.setDescription(data.getDescription());
            category.setParentCategory(data.getParentCategory());
            return ResponseEntity.ok(categoryRepository.save(category));
        }).orElse(ResponseEntity.notFound().build());
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
