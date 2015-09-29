package org.lucjross.uspresidential.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lucas on 11/24/2014.
 */
public class Vote implements Serializable {
    static final long serialVersionUID = 1L;

    private String user_username;
    private int event_id;
    private String vote;
    private short weight;
    private Timestamp timestamp;

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
