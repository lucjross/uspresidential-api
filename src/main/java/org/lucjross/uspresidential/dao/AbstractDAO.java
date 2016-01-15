package org.lucjross.uspresidential.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by lucas on 11/21/2014.
 */
@Repository
public abstract class AbstractDAO<T> implements DAO<T> {

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@SuppressWarnings("SpringJavaAutowiringInspection") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T find(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T update(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException();
    }
}
