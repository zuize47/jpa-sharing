package com.nashtech.sharing.jpa.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@MappedSuperclass
public class AuditEntity<ID extends java.io.Serializable> extends IdEntity<ID> {

    @PrePersist
    void onPrePersist () {
        var now = LocalDateTime.now(ZoneOffset.UTC);
        this.dateCreated = now;
        this.dateModified = now;
    }

    @PreUpdate
    void onPreUpdate () {
        this.dateModified = LocalDateTime.now(ZoneOffset.UTC);
    }

    @Column(name = "DATE_CREATED")
    LocalDateTime dateCreated;

    @Column(name = "DATE_MODIFIED")
    LocalDateTime dateModified;


}
