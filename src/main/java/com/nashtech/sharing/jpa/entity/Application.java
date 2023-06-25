package com.nashtech.sharing.jpa.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "APPLICATIONS")
@NamedQueries({
    @NamedQuery(
        name = "findEmployeeByPK",
        query = "SELECT a FROM Application a WHERE a.applicationId = :applicationId"
    )
})

@Data
@EntityListeners(AuditTrailListener.class)
public class Application {

    @PrePersist
    void onPrePersist () {
        if ( Objects.isNull(this.applicationId) || !this.getApplicationId().startsWith("APP_") ) {
            this.applicationId = String.format("APP_%012d", Long.valueOf(this.applicationId));
        }

    }

    @Id
    @SequenceGenerator(name = "application_seq",
        sequenceName = "application_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "application_seq")
    @Column(name = "ID", updatable = false)
    String applicationId;

    @Column(name = "NAME")
    String applicationName;

}
