package org.lucjross.uspresidential.model;

/**
 * Created by lucas on 1/30/16.
 */
public class EventAndVote {

    private final Event event;
    private final Vote vote;

    public EventAndVote(Event event, Vote vote) {
        this.event = event;
        this.vote = vote;
    }

    public Event getEvent() {
        return event;
    }

    public Vote getVote() {
        return vote;
    }
}
