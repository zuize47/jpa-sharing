package com.nashtech.sharing.jpa.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class GenericJPA<ENTITY, ID> {

    ThreadLocal<EntityManager> entityManagers = new InheritableThreadLocal<>();

    private final EntityManagerFactory emf;
    private final Class<ENTITY>        clazz;

    @SuppressWarnings("unchecked")
    GenericJPA (EntityManagerFactory emf) {
        var type = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        this.clazz = (Class<ENTITY>) (p.getActualTypeArguments()[0]);
        this.emf = emf;
    }

    public Optional<ENTITY> findOneById (ID id) {
        return readOnlyJpa(
            entityManager -> Optional.ofNullable(entityManager.find(this.clazz, id))

        );
    }

    public ENTITY save (final ENTITY entity) {
        return doInJpa(
            em ->
                getIdValue(entity)
                    .map(e -> em.merge(entity))
                    .orElseGet(() -> {
                        em.persist(entity);
                        return entity;
                    }));
    }

    public List<ENTITY> getAll () {
        return readOnlyJpa(
            em -> {
                var cb = em.getCriteriaBuilder();
                var cr = cb.createQuery(this.clazz);
                var from = cr.from(this.clazz);
                cr.select(from);
                return em.createQuery(cr).getResultList();
            }

        );
    }

    public ENTITY delete (ID id) {
        return this.findOneById(id)
            .map(this::deleteEntity)
            .orElseThrow();
    }

    public ENTITY deleteEntity (ENTITY entity) {
        return doInJpa(entityManager -> {
            entityManager.remove(entity);
            return entity;
        });

    }

    private EntityManager getEntityManager () {
        if ( entityManagers.get() == null || !entityManagers.get().isOpen()) {
            entityManagers.set(this.emf.createEntityManager());
        }
        return entityManagers.get();
    }

    public <T> T doInJpa (Function<EntityManager, T> func) {
        var em = this.getEntityManager();
        var transaction = em.getTransaction();
        var needCommit = false;
        if ( !transaction.isActive() ) {
            transaction.begin();
            needCommit = true;
        }
        try {
            T t = func.apply(em);
            if ( needCommit ) {
                transaction.commit();
            }
            return t;
        }
        catch (PersistenceException exception) {
            log.error(exception);
            if ( needCommit && transaction.isActive() ) {
                transaction.rollback();
            }
            throw exception;
        } finally {
            if( needCommit ) {
                em.close();
            }
        }
    }

    public <T> T readOnlyJpa (Function<EntityManager, T> func) {
        try (var em = this.emf.createEntityManager()) {
            return func.apply(em);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<Object> getIdValue (T entity) {
        return this.readOnlyJpa(
            entityManager ->
                Optional
                    .ofNullable(entityManager
                                    .getEntityManagerFactory()
                                    .getPersistenceUnitUtil()
                                    .getIdentifier(entity)));
    }

    @PreDestroy
    private void close () {
        if ( null != entityManagers.get() ) {
            entityManagers.get().close();
        }
    }
}
