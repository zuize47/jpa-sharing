package com.nashtech.sharing.jpa.service.impl;

import java.util.List;
import java.util.Optional;

import com.nashtech.sharing.jpa.dao.EnhancementDao;
import com.nashtech.sharing.jpa.entity.Enhancement;
import com.nashtech.sharing.jpa.service.EnhancementService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;

@ApplicationScoped
public class CdiEnhancementServiceImpl extends CdiGenericServiceImpl<Enhancement, Long> implements EnhancementService {

    private final EnhancementDao repository;

    @Inject
    public CdiEnhancementServiceImpl (EnhancementDao repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<Enhancement> findAndLock (Long id, int wait, int sleep, String task, LockModeType lockModeType) {
        return Optional.empty();
    }

    @Override
    public List<Enhancement> specificationExample (String id) {
        return null;
    }

    @Override
    public List<Enhancement> queryByExampleSample (String title) {
        return null;
    }
}
