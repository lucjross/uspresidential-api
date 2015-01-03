package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.PresidentDAO;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
@Repository
public class PresidentDAOImpl implements PresidentDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String TABLE = "p_presidents";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public President getPresident(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID=?";
        President president = jdbcTemplate.queryForObject(sql, new Object[] {id}, newMapper());
        return president;
    }

    @Override
    public List<President> getPresidentList() {
        String sql = "SELECT * FROM " + TABLE;
        List<President> presidents = jdbcTemplate.query(sql, newMapper());
        return presidents;
    }

    private static RowMapper<President> newMapper()
    {
        return BeanPropertyRowMapper.newInstance(President.class);
    }
}
