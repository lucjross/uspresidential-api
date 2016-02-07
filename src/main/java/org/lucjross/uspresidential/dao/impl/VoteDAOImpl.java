package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.VoteDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lucas on 11/24/2014.
 */
@Repository
public class VoteDAOImpl extends AbstractDAO<Vote> implements VoteDAO {

    static final String TABLE = " votes ";
    static final String GETVOTES_SQL = "select * from" + TABLE + "where event_id in (%s)";

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

    public Map<Integer, List<Vote>> getVotesByEvents(List<Integer> eventIds) {
        String params = String.join(",", Collections.nCopies(eventIds.size(), "?"));
        String sql = String.format(GETVOTES_SQL, params);
        List<Vote> votes = jdbcOps.query(sql, eventIds.toArray(), MAPPER);
        return votes.stream().collect(Collectors.groupingBy(Vote::getEvent_id));
    }


    static final RowMapper<Vote> MAPPER = new BeanPropertyRowMapper<>(Vote.class, true);
}
