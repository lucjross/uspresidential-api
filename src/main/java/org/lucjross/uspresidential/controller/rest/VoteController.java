package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.dao.VoteDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lucas on 11/25/2014.
 */
@RestController
public class VoteController {

    @Autowired
    private VoteDAO voteDAO;

    @RequestMapping(value="/vote", method=RequestMethod.GET)
    public Vote getVote(@RequestParam("id") Integer id) {
        Vote vote = voteDAO.find(id);
        return vote;
    }

    @RequestMapping(value="/votes", method=RequestMethod.GET)
    public List<Vote> getVotesByEvent(
            @RequestParam("event-id") Integer eventID) {
        Event event = new Event();
        event.setID(eventID);
        List<Vote> votes = voteDAO.getVotes(event);
        return votes;
    }
}
