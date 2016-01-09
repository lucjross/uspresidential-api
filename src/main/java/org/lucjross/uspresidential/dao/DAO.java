package org.lucjross.uspresidential.dao;

/**
 * Created by lucas on 11/25/2014.
 */
public interface DAO<T> {

    void create(T t);

    T find(Integer id);

    T update(T t);

    void delete(Integer id);
}
