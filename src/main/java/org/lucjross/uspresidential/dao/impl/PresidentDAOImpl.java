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
public class PresidentDAOImpl extends AbstractDAO<President, Integer> implements PresidentDAO {

    static final String TABLE = "presidents";

    @Override
    public President find(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        President president = jdbcTemplate.queryForObject(sql, new Object[] {id}, MAPPER);
        return president;
    }

    @Override
    public List<President> getPresidents() {
        String sql = "SELECT * FROM " + TABLE + " order by `order` asc";
        List<President> presidents = jdbcTemplate.query(sql, MAPPER);
        return presidents;
    }

    private static final RowMapper<President> MAPPER = new BeanPropertyRowMapper<>(President.class, true);
}
