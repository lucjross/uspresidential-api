package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.VoteDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collection;
import java.util.List;

/**
 * Created by lucas on 11/24/2014.
 */
@Repository
public class VoteDAOImpl extends AbstractDAO<Vote> implements VoteDAO {

    static final String TABLE = "votes";

    @Override
    public void create(Vote vote) {
        String sql = "INSERT INTO " + TABLE +
                " (user_username, event_id, vote, weight) " +
                " VALUES (?, ?, ?, ?)";
        Object[] o = new Object[] {vote.getUser_username(), vote.getEvent_id(),
                vote.getVote(), vote.getWeight()};
        jdbcTemplate.update(sql, o);
    }

    @Override
    public Vote find(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vote update(Vote vote) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Vote> getVotes(Event event) {
        String sql = "SELECT * FROM " + TABLE + " WHERE event_id=?";
        Object[] o = new Object[] {event.getId()};
        List<Vote> votes = jdbcTemplate.query(sql, o, MAPPER);
        return votes;
    }

    @Override
    public Collection<Vote> getVotes(int event_id, String user_username) {
        String sql = "select * from " + TABLE + " where event_id = ? and user_username = ?";
        Object[] o = new Object[] {event_id, user_username};
        List<Vote> votes = jdbcTemplate.query(sql, o, MAPPER);
        return votes;
    }

    @Override
    public void delete(String user_username) {
        String sql = "delete from " + TABLE + " where user_username = ?";
        Object[] o = new Object[] {user_username};
        jdbcTemplate.update(sql, o);
    }

    private static final RowMapper<Vote> MAPPER = new BeanPropertyRowMapper<>(Vote.class, true);
}
