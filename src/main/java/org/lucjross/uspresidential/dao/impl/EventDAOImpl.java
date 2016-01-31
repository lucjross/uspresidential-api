package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.EventDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.EventAndVote;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by lucas on 11/23/2014.
 */
@Repository
public class EventDAOImpl extends AbstractDAO<Event, Integer> implements EventDAO {

    private static final String TABLE = " events e\n";
    private static final String SELECT_LIST = " e.id,e.description,e.president_id,e.weight,e.importance,e.category," +
            "e.summary,e.start,e.end,e.wiki_link," +
            "v.user_username,v.event_id,v.response,v.voteWeight,v.created\n";
    private static final String LEFT_JOIN_VOTED = " left join votes v on " +
            "v.user_username = ? and v.event_id = e.id\n";
    private static final String ORDER_BY = " order by president_id, start\n";
    private static final String LIMIT_OFFSET = " limit ? offset ?\n";
    private static final String AND_NOT_VOTED = " and v.event_id is null\n";

    @Override
    public Event find(Integer id) {
        String sql = "select * from " + TABLE + " where id = ?";
        Event event = jdbcOps.queryForObject(sql, new Object[] {id}, MAPPER);
        return event;
    }

    @Override
    public List<Event> getAllEvents(int limit, int offset) {
        String sql = "select * from " + TABLE + ORDER_BY + LIMIT_OFFSET;
        List<Event> events = jdbcOps.query(sql, new Object[] {limit, offset}, MAPPER);
        return events;
    }

    @Override
    public List<EventAndVote> getEvents(int limit, int offset,
                                      boolean getAlreadyVoted, String username,
                                      int president_id) {
        List<Object> params = new LinkedList<>();

        String sql = "select" + SELECT_LIST + "from" + TABLE + LEFT_JOIN_VOTED;
        params.add(username);

        sql += "where president_id = ? ";
        params.add(president_id);

        if (! getAlreadyVoted) {
            sql += AND_NOT_VOTED;
        }

        sql += ORDER_BY + LIMIT_OFFSET;
        params.add(limit); params.add(offset);

        List<EventAndVote> events = jdbcOps.query(sql, params.toArray(), MAP_MAPPER);
        return events;
    }

    @Override
    public List<EventAndVote> getEventsForPeriod(int limit, int offset,
                                               boolean getAlreadyVoted, String username,
                                               Optional<java.sql.Date> start, Optional<java.sql.Date> end) {
        List<Object> params = new LinkedList<>();

        String sql = "select" + SELECT_LIST + "from" + TABLE + LEFT_JOIN_VOTED;
        params.add(username);

        sql += "where 1 = 1 ";

        if (start.isPresent()) {
            sql += "and start >= ? ";
            params.add(start.get());
        }

        if (end.isPresent()) {
            sql += "and end < ? ";
            params.add(end.get());
        }

        if (! getAlreadyVoted) {
            sql += AND_NOT_VOTED;
        }

        sql += ORDER_BY + LIMIT_OFFSET;
        params.add(limit); params.add(offset);

        List<EventAndVote> events = jdbcOps.query(sql, params.toArray(), MAP_MAPPER);
        return events;

    }


    static final RowMapper<Event> MAPPER = new BeanPropertyRowMapper<>(Event.class, true);

    static final RowMapper<EventAndVote> MAP_MAPPER = (rs, i) -> {
        Event e = MAPPER.mapRow(rs, i);
        Vote v = rs.getObject("response") == null ? null : VoteDAOImpl.MAPPER.mapRow(rs, i);
        return new EventAndVote(e, v);
    };
}
