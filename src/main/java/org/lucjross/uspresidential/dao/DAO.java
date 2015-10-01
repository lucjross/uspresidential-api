package org.lucjross.uspresidential.dao;

/**
 * Created by lucas on 11/25/2014.
 */
public interface DAO<T> {

    public void create(T t);

    public T find(Integer id);

    public T update(T t);

    public void delete(Integer id);
}
