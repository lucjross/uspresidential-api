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

    private static final String TABLE = "p_presidents";

    @Override
    public President find(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID=?";
        President president = jdbcTemplate.queryForObject(sql, new Object[] {id}, newMapper());
        return president;
    }

    @Override
    public int create(President president) {
        throw new UnsupportedOperationException();
    }

    @Override
    public President update(President president) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<President> getPresidents() {
        String sql = "SELECT * FROM " + TABLE;
        List<President> presidents = jdbcTemplate.query(sql, newMapper());
        return presidents;
    }

    private static RowMapper<President> newMapper()
    {
        return BeanPropertyRowMapper.newInstance(President.class);
    }
}
