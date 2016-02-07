package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.PresidentDAO;
import org.lucjross.uspresidential.model.President;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
@Repository
public class PresidentDAOImpl extends AbstractDAO<President> implements PresidentDAO {

    static final String TABLE = " presidents ";

    @Override
    public List<President> getPresidents() {
        String sql = "select * from" + TABLE + " order by `order`";
        List<President> presidents = jdbcOps.query(sql, MAPPER);
        return presidents;
    }

    static final RowMapper<President> MAPPER = new BeanPropertyRowMapper<>(President.class, true);
}
