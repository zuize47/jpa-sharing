package com.nashtech.sharing.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Table(name = "PAGES",
    uniqueConstraints = {
        @UniqueConstraint(name = "BOOK_PAGE_UNI", columnNames = { "PAGE_NUM", "BOOK_ID" })
    }
)
@Entity
@Data
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAGE_ID")
    Long pageId;

    @Column(name = "PAGE_NUM")
    Long pageNum;

    @Column(name = "BOOK_ID")
    Long bookId;
}
