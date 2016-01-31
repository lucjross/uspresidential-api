package org.lucjross.uspresidential.controller.rest;

import com.wordnik.swagger.annotations.ApiOperation;
import org.lucjross.uspresidential.RestApiConfig;
import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.EventDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.EventAndVote;
import org.lucjross.uspresidential.model.President;
import org.lucjross.uspresidential.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by lucas on 11/24/2014.
 */
@RestController
@RequestMapping(RestApiConfig.BASE_URI + "/event")
public class EventController {

    static final String DEFAULT_LIMIT = "10";

    @Autowired
    private EventDAO eventDAO;

    /**
     * Returns an event for an event ID.
     *
     * @param  id  An event ID
     * @return  An {@link Event}
     */
    @RequestMapping(method=RequestMethod.GET)
    public Event getEvent(
            @RequestParam("id") Integer id) {
        Event event = eventDAO.find(id);
        return event;
    }

    @RequestMapping(value="/for-period", method=RequestMethod.GET)
    @ApiOperation(value="Get events for period",
            notes="Gets all events that occurred during the given " +
            "date range. Events missing a start date or end date will be excluded.")
    public List<EventAndVote> getEventsForPeriod(
            Principal principal,
            @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT) int limit,
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "getAlreadyVoted", defaultValue = "false") boolean getAlreadyVoted,
            @RequestParam(value = "startYear", defaultValue = "1700") int startYear,
            @RequestParam(value = "endYear", defaultValue = "2400") int endYear) {

        String username = principal.getName();
        java.sql.Date start = java.sql.Date.valueOf(startYear + "-1-1");
        java.sql.Date end = java.sql.Date.valueOf(endYear + "-1-1");
        List<EventAndVote> events = eventDAO.getEventsForPeriod(
                limit, offset, getAlreadyVoted, username,
                Optional.of(start), Optional.of(end));
        return events;
    }

    @RequestMapping(value="/by-president", method=RequestMethod.GET)
    public List<EventAndVote> getEventsByPresident(
            Principal principal,
            @RequestParam(value = "limit", defaultValue = DEFAULT_LIMIT) int limit,
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "getAlreadyVoted", defaultValue = "false") boolean getAlreadyVoted,
            @RequestParam(value = "presidentId") int presidentId) {

        String username = principal.getName();
        List<EventAndVote> events = eventDAO.getEvents(
                limit, offset, getAlreadyVoted, username, presidentId);
        return events;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Event> getAllEvents() {
        List<Event> events = eventDAO.getAllEvents(10, 0); // todo
        return events;
    }

}
