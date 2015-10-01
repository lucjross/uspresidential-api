package org.lucjross.uspresidential.dao;

import org.junit.Assert;
import org.junit.Test;
import org.lucjross.uspresidential.TestCase;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Created by lucas on 12/28/2014.
 */
public class VoteDAOTest extends TestCase {

    @Autowired
    private VoteDAO voteDAO;

    @Test
    public void testCreate() throws Exception {
        voteDAO.delete("user0");

        Vote vote1 = new Vote();
        vote1.setUser_username("user0");
        vote1.setEvent_id(1);
        vote1.setVote("Yes");
        vote1.setWeight((short) 5);
        voteDAO.create(vote1);

        Collection<Vote> votes = voteDAO.getVotes(1, "user0");
        Assert.assertEquals(1, votes.size());
        Vote v = votes.iterator().next();
        Assert.assertEquals("user0", v.getUser_username());
        Assert.assertEquals(1, v.getEvent_id());
        Assert.assertEquals("Yes", v.getVote());
        Assert.assertEquals(5, v.getWeight());
        Assert.assertNotNull(v.getTimestamp());
    }
}
