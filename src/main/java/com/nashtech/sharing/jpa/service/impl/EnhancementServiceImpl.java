package com.nashtech.sharing.jpa.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.sharing.jpa.JpaShareApplication;
import com.nashtech.sharing.jpa.entity.Enhancement;
import com.nashtech.sharing.jpa.repository.EnhancementRepository;
import com.nashtech.sharing.jpa.service.EnhancementService;
import jakarta.persistence.LockModeType;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EnhancementServiceImpl extends GenericServiceImpl<Enhancement, Long> implements EnhancementService {

    private final EnhancementRepository repository;

    public EnhancementServiceImpl (EnhancementRepository enhancementRepository) {
        super(enhancementRepository);
        this.repository = enhancementRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 5)
    public Optional<Enhancement> findAndLock (Long id, int wait, int sleep, String task, LockModeType lockModeType) {
        JpaShareApplication.sleep(wait);
        String name = Thread.currentThread().getName();
        log.info("start_read on thread {} {} {}", name, task, id);
        var optionalEnhance = switch (lockModeType) {
            case PESSIMISTIC_READ -> this.repository.findByIdAndLock(id);
            case PESSIMISTIC_WRITE -> this.repository.findByIdAndWriteLock(id);
            case PESSIMISTIC_FORCE_INCREMENT -> this.repository.findByIdAndWrite2Lock(id);
            default -> this.repository.findByIdAndNoLock(id);
        };
        Enhancement enhancement = optionalEnhance.get();
        log.info("end_read on thread  {} {} {}", name, task, id);
        JpaShareApplication.sleep(sleep);
        // long process
        enhancement.setTitle(String.format("%s##%s", id, LocalDateTime.now()));
        repository.save(enhancement);
        log.info("write success {} {} {}", name, task, id);
        return Optional.of(enhancement);
    }

    @Override
    public List<Enhancement> specificationExample (String title) {
        Specification<Enhancement> specification = (root, query, criteriaBuilder) -> {
            // Create predicates based on your criteria
            var namePredicate = criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
//            var pricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), 100.0);

            // Combine the predicates using conjunction (AND) or disjunction (OR)
            return criteriaBuilder.and(namePredicate);
        };

        return repository.findAll(specification);
    }

    public List<Enhancement> queryByExampleSample(String title) {
        Enhancement exampleProduct = new Enhancement();
        exampleProduct.setTitle(title);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
            .withIgnoreCase()
            .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Enhancement> example = Example.of(exampleProduct, exampleMatcher);

        return repository.findAll(example);
    }
}
