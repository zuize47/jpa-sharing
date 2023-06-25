package com.nashtech.sharing.jpa.service;

import java.util.List;
import java.util.Optional;

import com.nashtech.sharing.jpa.entity.Enhancement;
import jakarta.persistence.LockModeType;

public interface EnhancementService extends GenericService<Enhancement, Long> {

    Optional<Enhancement> findAndLock(Long id, int wait, int sleep, String task, LockModeType lockModeType);

    List<Enhancement> specificationExample (String id);

    List<Enhancement> queryByExampleSample(String title);

}
