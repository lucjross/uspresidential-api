package org.lucjross.uspresidential.dao;

/**
 * Created by lucas on 11/25/2014.
 */
public interface DAO<T, K> {

    void create(T t);

    T find(K id);

    T update(T t);

    void delete(K id);
}
