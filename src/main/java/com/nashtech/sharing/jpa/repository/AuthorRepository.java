package com.nashtech.sharing.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.sharing.jpa.dto.AuthorDto;
import com.nashtech.sharing.jpa.dto.AuthorProjection;
import com.nashtech.sharing.jpa.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a from Author a")
    List<AuthorProjection> findAllProjectedBy ();

    @Query("SELECT new com.nashtech.sharing.jpa.dto.AuthorDto(a.authorId, a.name, a.name, ad.address) FROM Author a join a.authorDetail ad")
    List<AuthorDto> findAllAsDTO();
}
