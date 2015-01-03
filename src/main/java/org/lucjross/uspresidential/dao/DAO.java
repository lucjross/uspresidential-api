package org.lucjross.uspresidential.dao;

/**
 * Created by lucas on 11/25/2014.
 */
public interface DAO<T> {

    /**
     * Inserts a row.
     *
     * @param t  An instance of {@link T}.
     * @return  The ID of the inserted row.
     */
    public int create(T t);

    public T find(Object id);

    public T update(T t);

    public void delete(Object id);
}
