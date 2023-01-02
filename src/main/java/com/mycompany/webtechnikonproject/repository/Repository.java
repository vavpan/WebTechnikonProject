package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.model.PersistentClass;
import java.util.List;

public interface Repository<T extends PersistentClass> {

    int create(T t);

    List<T> findAll();

//    boolean deleteEntity(int id);
}
