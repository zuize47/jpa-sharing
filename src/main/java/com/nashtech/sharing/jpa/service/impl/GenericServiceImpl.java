package com.nashtech.sharing.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.sharing.jpa.service.GenericService;

public class GenericServiceImpl<ENTITY, ID> implements GenericService<ENTITY, ID> {

    final private JpaRepository<ENTITY, ID> repository;

    public GenericServiceImpl (JpaRepository<ENTITY, ID> repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public ENTITY save (ENTITY entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<ENTITY> findOneById (ID id) {
        return repository.findById(id);
    }

    @Override
    public List<ENTITY> findAll () {
        return repository.findAll();
    }

    @Override
    @Transactional
    public ENTITY delete (ID id) {
        return this.findOneById(id).map(this::deleteEntity)
            .orElseThrow();
    }

    @Override
    @Transactional
    public ENTITY deleteEntity (ENTITY entity) {
        this.repository.delete(entity);
        return entity;
    }


}
