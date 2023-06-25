package com.nashtech.sharing.jpa.dao;

import com.nashtech.sharing.jpa.entity.Bug;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManagerFactory;

@ApplicationScoped
public class BugDao extends GenericJPA<Bug, Long> {

    @jakarta.inject.Inject
    BugDao (EntityManagerFactory emf) {
        super(emf);
    }
}
