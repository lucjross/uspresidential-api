package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.VoteDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by lucas on 11/24/2014.
 */
@Repository
public class VoteDAOImpl extends AbstractDAO<Vote, Integer> implements VoteDAO {

    static final String TABLE = " votes ";

    @Override
    public void create(Vote vote) {
        String sql = "insert into" + TABLE +
                " (user_username, event_id, response, voteWeight) " +
                " values (?, ?, ?, ?)";
        Object[] o = new Object[] {vote.getUser_username(), vote.getEvent_id(),
                vote.getResponse().name(), vote.getVoteWeight()};
        jdbcOps.update(sql, o);
    }

    @Override
    public int update(Vote vote) {
        String sql = "update" + TABLE + "set " +
                "response = ?, voteWeight = ? " +
                "where user_username = ? and event_id = ?";
        Object[] o = new Object[] {
                vote.getResponse().name(), vote.getVoteWeight(),
                vote.getUser_username(), vote.getEvent_id()};
        return jdbcOps.update(sql, o);
    }

    @Override
    public Collection<Vote> getVotes(Event event) {
        String sql = "SELECT * FROM " + TABLE + " WHERE event_id = ?";
        Object[] o = new Object[] {event.getId()};
        List<Vote> votes = jdbcOps.query(sql, o, MAPPER);
        return votes;
    }

    @Override
    public Collection<Vote> getVotes(int event_id, String user_username) {
        String sql = "select * from " + TABLE + " where event_id = ? and user_username = ?";
        Object[] o = new Object[] {event_id, user_username};
        List<Vote> votes = jdbcOps.query(sql, o, MAPPER);
        return votes;
    }

    @Override
    public void delete(String user_username) {
        String sql = "delete from " + TABLE + " where user_username = ?";
        Object[] o = new Object[] {user_username};
        jdbcOps.update(sql, o);
    }

    static final RowMapper<Vote> MAPPER = new BeanPropertyRowMapper<>(Vote.class, true);
}
