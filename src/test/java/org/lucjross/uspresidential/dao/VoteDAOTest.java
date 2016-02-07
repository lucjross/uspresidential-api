package org.lucjross.uspresidential.dao;

import org.junit.Test;
import org.lucjross.uspresidential.TestCase;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lucas on 12/28/2014.
 */
public class VoteDAOTest extends TestCase {

    @Autowired
    private VoteDAO voteDAO;

    @Test
    public void testCreate() throws Exception {
        Vote key = new Vote();
        key.setUser_username("user0");
        voteDAO.delete(key);

        Vote vote1 = new Vote();
        vote1.setUser_username("user0");
        vote1.setEvent_id(1);
        vote1.setResponse(Vote.Response.YES);
        vote1.setVoteWeight((short) 5);
        voteDAO.create(vote1);
    }
}
