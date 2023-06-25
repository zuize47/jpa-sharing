package com.nashtech.sharing.jpa.entity;


import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "BOOKS")
@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    Long bookId;

    @Column(name = "BOOK_NAME", nullable = false)
    String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID", foreignKey = @ForeignKey(name = "PAGE_BOOK_FK"))
    Set<Page> pages;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID", foreignKey = @ForeignKey(name = "BOOK_AUTHOR_FK"))
    Author author;

}
