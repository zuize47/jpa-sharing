package com.nashtech.sharing.jpa.entity;


import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuditTrailListener {

    //    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate (Application application) {
        if ( application.getApplicationId() == null ) {
            log.info("[APPLICATION AUDIT] About to add a application");
        }
        else {
            log.info("[APPLICATION AUDIT] About to update/delete application: " + application.getApplicationId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate (Application application) {
        log.info("[APPLICATION AUDIT] add/update/delete complete for application: " + application.getApplicationId());
    }

    @PostLoad
    private void afterLoad (Application application) {
        log.info("[APPLICATION AUDIT] loaded from database: " + application.getApplicationId());
    }
}
