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
import java.util.List;

/**
 * Created by lucas on 11/24/2014.
 */
@Repository
public class VoteDAOImpl extends AbstractDAO<Vote> implements VoteDAO {

    private static final String TABLE = "p_votes";

    @Override
    public int create(Vote vote) {
        String sql = "INSERT INTO " + TABLE +
                " (user_id, event_id, vote, timestamp) " +
                " VALUES (?, ?, ?, ?)";
        Object[] o = new Object[] {vote.getUser_id(), vote.getEvent_id(),
                vote.getVote(), vote.getTimestamp()};
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(sql,
                        Types.INTEGER, Types.INTEGER, Types.TINYINT, Types.TIMESTAMP);
        pscf.setGeneratedKeysColumnNames("id");
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(o);
        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(psc, kh);
        return kh.getKey().intValue();
    }

    @Override
    public Vote find(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id=?";
        Vote vote = jdbcTemplate.queryForObject(
                sql, new Object[] {id}, newMapper());
        return vote;
    }

    @Override
    public Vote update(Vote vote) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Vote> getVotes(Event event) {
        String sql = "SELECT * FROM " + TABLE + " WHERE event_id=?";
        Object[] o = new Object[] {event.getID()};
        List<Vote> votes = jdbcTemplate.query(sql, o, newMapper());
        return votes;
    }

    private static RowMapper<Vote> newMapper()
    {
        return BeanPropertyRowMapper.newInstance(Vote.class);
    }
}
