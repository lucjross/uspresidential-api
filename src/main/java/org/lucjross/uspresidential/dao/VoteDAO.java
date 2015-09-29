package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;

import java.util.Collection;
import java.util.List;

/**
 * Created by lucas on 11/24/2014.
 */
public interface VoteDAO extends DAO<Vote> {

    Collection<Vote> getVotes(Event event);

    Collection<Vote> getVotes(int event_id, String user_username);

    void delete(String user_username);
}
