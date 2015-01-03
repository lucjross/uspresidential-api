package org.lucjross.uspresidential.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lucas on 11/24/2014.
 */
public class Vote implements Serializable {
    static final long serialVersionUID = 1L;

    private int id;
    private int user_id;
    private int event_id;
    private short vote;
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public short getVote() {
        return vote;
    }

    public void setVote(short vote) {
        this.vote = vote;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
