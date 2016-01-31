package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.EventAndVote;
import org.lucjross.uspresidential.model.Vote;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by lucas on 11/23/2014.
 */
public interface EventDAO extends DAO<Event, Integer> {

    List<Event> getAllEvents(int limit, int offset);

    List<EventAndVote> getEvents(int limit, int offset,
                                 boolean getAlreadyVoted, String username,
                                 int president_id);

    /**
     *
     * @param limit
     * @param offset
     * @param getAlreadyVoted
     * @param username
     * @param start  inclusive
     * @param end  exclusive
     * @return
     */
    List<EventAndVote> getEventsForPeriod(int limit, int offset,
                                          boolean getAlreadyVoted, String username,
                                          Optional<java.sql.Date> start, Optional<java.sql.Date> end);
}
