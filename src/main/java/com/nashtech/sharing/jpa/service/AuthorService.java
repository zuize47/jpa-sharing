package com.nashtech.sharing.jpa.service;

import java.util.List;

import com.nashtech.sharing.jpa.dto.AuthorDto;
import com.nashtech.sharing.jpa.dto.AuthorProjection;

public interface AuthorService extends GenericService<com.nashtech.sharing.jpa.entity.Author, Long> {
    List<AuthorProjection> getFullAuthor();

    List<AuthorDto> getAuthorProjection();
}
