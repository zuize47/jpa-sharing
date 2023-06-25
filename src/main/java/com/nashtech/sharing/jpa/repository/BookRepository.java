package com.nashtech.sharing.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.sharing.jpa.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
