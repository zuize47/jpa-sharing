package com.nashtech.sharing.jpa.dao;

import com.nashtech.sharing.jpa.entity.Author;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManagerFactory;

@ApplicationScoped
public class AuthorDao extends GenericJPA<Author, Long> {
    @jakarta.inject.Inject
    AuthorDao (EntityManagerFactory emf) {
        super(emf);
    }
}
