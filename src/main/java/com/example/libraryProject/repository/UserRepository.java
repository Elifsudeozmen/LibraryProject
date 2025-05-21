package com.example.libraryProject.repository;
import com.example.libraryProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

}
