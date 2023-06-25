package com.nashtech.sharing.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@MappedSuperclass
public class Ticket extends AuditEntity<Long> {

    @Size(max = 100, min = 5)
    @Column(name = "TITLE", length = 256)
    String title;
    @Column(name = "DESCRIPTION", length = 40000)
    String description;

    @Version
    Long version;


}
