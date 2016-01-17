package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.EventDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.President;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
@Repository
public class EventDAOImpl extends AbstractDAO<Event, Integer> implements EventDAO {

    private static final String TABLE = "events";

    @Override
    public Event find(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        Event event = jdbcTemplate.queryForObject(sql, new Object[] {id}, MAPPER);
        return event;
    }

    @Override
    public Collection<Event> getEvents(int president_id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE president_id = ?";
        Object[] o = new Object[] { president_id };
        List<Event> events = jdbcTemplate.query(sql, o, MAPPER);
        return events;
    }

    @Override
    public Collection<Event> getEventsForPeriod(
            int president_id, java.sql.Date start, java.sql.Date end) {
        String sql =
                "SELECT * FROM " + TABLE + " WHERE president_id = ? " +
                "AND NOT (start > ? OR end < ?)";
        Object[] o = new Object[] { president_id, end, start };
        List<Event> events = jdbcTemplate.query(sql, o, MAPPER);
        return events;

    }

    private static final RowMapper<Event> MAPPER = new BeanPropertyRowMapper<>(Event.class, true);
}
