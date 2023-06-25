package com.nashtech.sharing.jpa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.sharing.jpa.dto.AuthorDto;
import com.nashtech.sharing.jpa.dto.AuthorProjection;
import com.nashtech.sharing.jpa.repository.AuthorRepository;
import com.nashtech.sharing.jpa.service.AuthorService;

@Service
@Transactional(readOnly = true)
public class AuthorServiceImpl extends GenericServiceImpl<com.nashtech.sharing.jpa.entity.Author, Long> implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl (AuthorRepository authorRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
    }

    public List<AuthorProjection> getFullAuthor() {
        return this.authorRepository.findAllProjectedBy();
    }

    @Override
    public List<AuthorDto> getAuthorProjection () {
        return this.authorRepository.findAllAsDTO();
    }
}
