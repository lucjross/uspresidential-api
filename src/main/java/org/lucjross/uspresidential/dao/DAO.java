package org.lucjross.uspresidential.dao;

/**
 * Created by lucas on 11/25/2014.
 */
public interface DAO<T, K> {

    default void create(T t) {
        throw new UnsupportedOperationException();
    }

    default T find(K id) {
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

    default void delete(K id) {
        throw new UnsupportedOperationException();
    }
}
