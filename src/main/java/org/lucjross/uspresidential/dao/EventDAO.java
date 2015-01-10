package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.President;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
public interface EventDAO extends DAO<Event> {

    public List<Event> getEvents(President president);

    public List<Event> getEventsForPeriod(
            President president, java.sql.Date start, java.sql.Date end);
}
