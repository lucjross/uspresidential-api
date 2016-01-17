package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.Event;

import java.util.Collection;

/**
 * Created by lucas on 11/23/2014.
 */
public interface EventDAO extends DAO<Event, Integer> {

    Collection<Event> getEvents(int president_id);

    Collection<Event> getEventsForPeriod(int president_id, java.sql.Date start, java.sql.Date end);
}
