package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.model.PersistentClass;

public interface Repository<T extends PersistentClass> {

    int create(T t);
}
