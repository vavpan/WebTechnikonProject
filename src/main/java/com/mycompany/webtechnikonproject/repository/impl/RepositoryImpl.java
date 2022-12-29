package com.mycompany.webtechnikonproject.repository.impl;

import com.mycompany.webtechnikonproject.model.PersistentClass;
import com.mycompany.webtechnikonproject.repository.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class RepositoryImpl<T extends PersistentClass> implements Repository<T> {
    
    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public int create(T t) {
        entityManager.persist(t);
        return t.getId();
    }
}
