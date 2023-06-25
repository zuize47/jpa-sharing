package com.nashtech.sharing.jpa.service.impl;

import java.util.List;

import com.nashtech.sharing.jpa.dao.AuthorDao;
import com.nashtech.sharing.jpa.dto.AuthorDto;
import com.nashtech.sharing.jpa.dto.AuthorProjection;
import com.nashtech.sharing.jpa.service.AuthorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CdiAuthorServiceImpl extends CdiGenericServiceImpl<com.nashtech.sharing.jpa.entity.Author, Long> implements AuthorService {

    private final AuthorDao repository;

    @Inject
    public CdiAuthorServiceImpl (AuthorDao repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<AuthorProjection> getFullAuthor () {
        return null;
    }

    @Override
    public List<AuthorDto> getAuthorProjection () {
        return null;
    }
}
