package org.lucjross.uspresidential.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public abstract class AbstractDAO<T> implements DAO<T> {

    @Autowired protected JdbcOperations jdbcOps;
}
