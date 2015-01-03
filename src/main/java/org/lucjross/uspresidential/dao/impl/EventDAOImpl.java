package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.EventDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
@Repository
public class EventDAOImpl extends AbstractDAO<Event> implements EventDAO {

    private static final String TABLE = "p_events";

    @Override
    public int create(Event event) {
        return -1;
    }

    @Override
    public Event find(Object id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE ID=?";
        Event event = jdbcTemplate.queryForObject(sql, new Object[] {id}, newMapper());
        return event;
    }

    @Override
    public Event update(Event event) {
        return null;
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List<Event> getEvents(President president) {
        String sql = "SELECT * FROM " + TABLE + " WHERE president_id=?";
        Object[] o = new Integer[] { president.getID() };
        List<Event> events = jdbcTemplate.query(sql, o, newMapper());
        return events;
    }

    private static RowMapper<Event> newMapper()
    {
        return BeanPropertyRowMapper.newInstance(Event.class);
    }
}
