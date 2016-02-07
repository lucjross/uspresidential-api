package org.lucjross.uspresidential.dao;

/**
 * Created by lucas on 11/25/2014.
 */
public interface DAO<T> {

    default void create(T t) {
        throw new UnsupportedOperationException();
    }

    default T find(T t) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param t
     * @return  number of rows updated
     */
    default int update(T t) {
        throw new UnsupportedOperationException();
    }

    default void delete(T t) {
        throw new UnsupportedOperationException();
    }
}
