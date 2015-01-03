package org.lucjross.uspresidential.dao;

import org.junit.Assert;
import org.junit.Test;
import org.lucjross.uspresidential.TestCase;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by lucas on 12/28/2014.
 */
public class VoteDAOTest extends TestCase {

    @Autowired
    private VoteDAO voteDAO;

    @Test
    public void testCreate() {
        Vote vote1 = new Vote();
        vote1.setUser_id(1);
        vote1.setEvent_id(3);
        vote1.setVote((short) 5);
        vote1.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        int key1 = voteDAO.create(vote1);
        voteDAO.delete(key1);
        try {
            vote1 = voteDAO.find(key1);
            Assert.fail();
        }
        catch (DataAccessException e) {}


    }
}
