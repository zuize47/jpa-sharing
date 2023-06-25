package com.nashtech.sharing.jpa.entity;


import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.ToString;

@Table(name = "AUTHORS")
@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHOR_ID")
    Long authorId;


    @Column(name = "NAME", length = 100)
    String name;

    @Column(name = "EMAIL", unique = true, nullable = false)
    @Email
    String email;

    @OneToMany(mappedBy = "author")
    Set<Book> book;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AUTHOR_DETAIL_ID", foreignKey = @ForeignKey(name = "AUTHORS_DETAIL_FK"))
    @ToString.Exclude
    AuthorDetail authorDetail;

    @Version
    @ToString.Exclude
    private int version;


}
