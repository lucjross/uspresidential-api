package org.lucjross.uspresidential.controller.rest;

import org.lucjross.uspresidential.RestApiConfig;
import org.lucjross.uspresidential.dao.VoteDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Created by lucas on 11/25/2014.
 */
@RestController
@RequestMapping(RestApiConfig.BASE_URI + "/vote")
public class VoteController {

    @Autowired
    private VoteDAO voteDAO;

    @RequestMapping(value="/by-event", method=RequestMethod.GET)
    public Collection<Vote> getVotesByEvent(
            @RequestParam("event-id") Integer eventID) {
        Event event = new Event();
        event.setId(eventID);
        Collection<Vote> votes = voteDAO.getVotes(event);
        return votes;
    }
}
