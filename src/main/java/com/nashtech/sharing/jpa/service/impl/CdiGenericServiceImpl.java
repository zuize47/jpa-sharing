package com.nashtech.sharing.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import com.nashtech.sharing.jpa.dao.GenericJPA;
import com.nashtech.sharing.jpa.service.GenericService;
import jakarta.transaction.Transactional;

public class CdiGenericServiceImpl<ENTITY, ID> implements GenericService<ENTITY, ID> {

    private final GenericJPA<ENTITY, ID> genericJPA;

    public CdiGenericServiceImpl (GenericJPA<ENTITY, ID> genericJPA) {
        this.genericJPA = genericJPA;
    }

    @Override
    public ENTITY save (ENTITY entity) {

        return this.genericJPA.save(entity);
    }

    @Override
    public Optional<ENTITY> findOneById (ID id) {
        return this.genericJPA.findOneById(id);
    }

    @Override

    public List<ENTITY> findAll () {
        return this.genericJPA.getAll();
    }

    @Override
    public ENTITY delete (ID id) {
        return genericJPA.delete(id);
    }

    @Override
    public ENTITY deleteEntity (ENTITY entity) {
        return genericJPA.deleteEntity(entity);
    }
}
