package org.lucjross.uspresidential.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by lucas on 11/21/2014.
 */
@Repository
public abstract class AbstractDAO<T, K> implements DAO<T, K> {

    protected JdbcOperations jdbcOps;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcOps = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T find(K id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(K id) {
        throw new UnsupportedOperationException();
    }
}
