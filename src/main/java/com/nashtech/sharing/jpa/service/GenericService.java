package com.nashtech.sharing.jpa.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<ENTITY, ID> {

    ENTITY save(ENTITY entity);

    Optional<ENTITY> findOneById(ID id);

    List<ENTITY> findAll();

    ENTITY delete(ID id);

    ENTITY deleteEntity(ENTITY entity);


}
