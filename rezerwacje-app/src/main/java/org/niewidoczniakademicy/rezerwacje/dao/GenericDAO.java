package org.niewidoczniakademicy.rezerwacje.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {

    T save(T entity);

    List<T> save(Iterable<T> entities);

    T update(T entity);

    void delete(T entity);

    void delete(Long id);

    void delete(List<T> entities);

    Optional<T> find(Long id);

    List<T> findAll();


}
