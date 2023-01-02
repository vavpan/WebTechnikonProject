package com.mycompany.webtechnikonproject.repository.impl;

import com.mycompany.webtechnikonproject.model.PersistentClass;
import com.mycompany.webtechnikonproject.repository.Repository;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.io.Serializable;
import java.util.List;


public abstract class RepositoryImpl<T extends PersistentClass> implements Repository<T> {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    @Override
    @Transactional
    public int create(T t) {
        entityManager.persist(t);
        return t.getId();
    }

    public abstract String getClassName();

    public abstract Class<T> getClassType();

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + getClassName()).getResultList();
    }

    @Override
    public boolean deleteEntity(int id) {
        T persistentInstance = entityManager.find(getClassType(), id);
        if (persistentInstance != null) {
            try {
                userTransaction.begin();
                entityManager.remove(entityManager.contains(persistentInstance) ? persistentInstance : entityManager.merge(persistentInstance));
                userTransaction.commit();
            } catch (Exception e) {
                System.out.println(e.toString());
                return false;
            }
            return true;
        }
        return false;
    }
}
