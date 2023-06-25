package com.nashtech.sharing.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.sharing.jpa.entity.Enhancement;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

@Repository
public interface EnhancementRepository extends JpaRepository<Enhancement, Long>, JpaSpecificationExecutor<Enhancement> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "1000") })
    @Query("SELECT e from Enhancement e where e.id = :id")
    Optional<Enhancement> findByIdAndLock (@Param("id") Long id);

    @Query("SELECT e from Enhancement e where e.id = :id")
    Optional<Enhancement> findByIdAndNoLock (@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "1000") })
    @Query("SELECT e from Enhancement e where e.id = :id")
    Optional<Enhancement> findByIdAndWriteLock (@Param("id") Long id);


    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "1000") })
    @Query("SELECT e from Enhancement e where e.id = :id")
    Optional<Enhancement> findByIdAndWrite2Lock (@Param("id") Long id);
}
