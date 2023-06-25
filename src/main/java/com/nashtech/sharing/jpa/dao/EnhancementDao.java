package com.nashtech.sharing.jpa.dao;

import com.nashtech.sharing.jpa.entity.Enhancement;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManagerFactory;

@ApplicationScoped
public class EnhancementDao extends GenericJPA<Enhancement, Long> {

    @jakarta.inject.Inject
    EnhancementDao (EntityManagerFactory emf) {
        super(emf);
    }
}
