package org.lucjross.uspresidential.controller;

import org.lucjross.uspresidential.dao.EventDAO;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lucas on 11/24/2014.
 */
@RestController
public class EventController {

    @Autowired
    private EventDAO eventDAO;

    /**
     * Returns an event for an event ID.
     *
     * @param  id  An event ID.
     * @return  An {@link Event}.
     */
    @RequestMapping(value="/event", method=RequestMethod.GET)
    public Event getEvent(
            @RequestParam("id") Integer id) {
        Event event = eventDAO.find(id);
        return event;
    }

    /**
     * Returns all events for a given president.
     *
     * @param  presidentID  A {@link President} ID.
     * @return  A map of event IDs to {@link Event}s.
     */
    @RequestMapping(value="/events", method=RequestMethod.GET)
    public List<Event> getEventsByPresident(
            @RequestParam("president-id") Integer presidentID) {
        President president = new President();
        president.setID(presidentID);
        List<Event> events = eventDAO.getEvents(president);
        return events;
    }

}
