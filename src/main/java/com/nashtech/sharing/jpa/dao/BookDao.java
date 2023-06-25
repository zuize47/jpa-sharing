package com.nashtech.sharing.jpa.dao;

import com.nashtech.sharing.jpa.entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManagerFactory;

@ApplicationScoped
public class BookDao extends GenericJPA<Book, Long> {

    @jakarta.inject.Inject
    public BookDao (EntityManagerFactory emf) {
        super(emf);
    }
}
