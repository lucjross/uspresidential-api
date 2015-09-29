package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.EventDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.President;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
@Repository
public class EventDAOImpl extends AbstractDAO<Event> implements EventDAO {

    private static final String TABLE = "events";

    @Override
    public void create(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Event find(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id=?";
        Event event = jdbcTemplate.queryForObject(sql, new Object[] {id}, newMapper());
        return event;
    }

    @Override
    public Event update(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Event> getEvents(President president) {
        String sql = "SELECT * FROM " + TABLE + " WHERE president_id=?";
        Object[] o = new Integer[] { president.getId() };
        List<Event> events = jdbcTemplate.query(sql, o, newMapper());
        return events;
    }

    @Override
    public List<Event> getEventsForPeriod(
            President president, java.sql.Date start, java.sql.Date end) {
        String sql = "SELECT * FROM " + TABLE + " WHERE president_id = ?" +
                " AND NOT (start > ? OR end < ?)";
        Object[] o = new Object[] { president.getId(), end, start };
        List<Event> events = jdbcTemplate.query(sql, o, newMapper());
        return events;

    }

    private static RowMapper<Event> newMapper()
    {
        return BeanPropertyRowMapper.newInstance(Event.class);
    }
}
