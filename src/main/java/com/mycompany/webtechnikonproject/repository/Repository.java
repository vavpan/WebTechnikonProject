package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.model.PersistentClass;
import java.util.List;
import java.util.Optional;

public interface Repository<T extends PersistentClass> {

    int create(T t);



    T read(int id);

    Optional<T> findEntityById(int id);

    boolean deleteEntity(int id);

    Optional<T> update(int id, T t);
    
   

}
