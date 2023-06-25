package com.nashtech.sharing.jpa.dao;

import com.nashtech.sharing.jpa.entity.Application;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManagerFactory;

@ApplicationScoped
public class ApplicationDao extends GenericJPA<Application, String> {
    @jakarta.inject.Inject
    ApplicationDao (EntityManagerFactory emf) {
        super(emf);
    }
}
