package org.lucjross.uspresidential.controller.rest;

import com.wordnik.swagger.annotations.ApiOperation;
import org.lucjross.uspresidential.RestApiConfig;
import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.EventDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Created by lucas on 11/24/2014.
 */
@RestController
@RequestMapping(RestApiConfig.BASE_URI + "/event")
public class EventController {

    static final String MIN_DATE = "1600-01-01";
    static final String MAX_DATE = "2400-01-01";

    @Autowired
    private EventDAO eventDAO;

    /**
     * Returns an event for an event ID.
     *
     * @param  id  An event ID.s
     * @return  An {@link Event}.
     */
    @RequestMapping(method=RequestMethod.GET)
    public Event getEvent(
            @RequestParam("id") Integer id) {
        Event event = eventDAO.find(id);
        return event;
    }

    @RequestMapping(value="/by-president-for-period", method=RequestMethod.GET)
    @ApiOperation(value="Get events for period",
            notes="Gets all of a president's events that occurred at least partially during the given" +
            " date range. Events missing a start date or end date will be excluded.")
    public Collection<Event> getEventsByPresidentForPeriod(
            @RequestParam("president-id") Integer presidentID,
            @RequestParam(value="start-date", defaultValue=MIN_DATE) String startDate,
            @RequestParam(value="end-date", defaultValue=MAX_DATE) String endDate) {
        java.sql.Date start = java.sql.Date.valueOf(startDate);
        java.sql.Date end = java.sql.Date.valueOf(endDate);
        Collection<Event> events = eventDAO.getEventsForPeriod(presidentID, start, end);
        return events;
    }

    /**
     * Returns all events for a given president.
     *
     * @param  presidentID  A {@link President} ID.
     *
     * @return  A map of event IDs to {@link Event}s.
     */
    @RequestMapping(value="/by-president", method=RequestMethod.GET)
    public Collection<Event> getEventsByPresident(
            @RequestParam("president-id") Integer presidentID) {
        President president = new President();
        president.setId(presidentID);
        Collection<Event> events = eventDAO.getEvents(presidentID);
        return events;
    }

}
