package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.Vote;

import java.util.List;

/**
 * Created by lucas on 11/24/2014.
 */
public interface VoteDAO extends DAO<Vote> {

    public List<Vote> getVotes(Event event);
}
