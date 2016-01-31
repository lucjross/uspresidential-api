package org.lucjross.uspresidential.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Vote implements Serializable {

    static final long serialVersionUID = 1L;

    private String user_username;
    private int event_id;
    private Response response;
    private short voteWeight;
    private java.sql.Timestamp created;

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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public short getVoteWeight() {
        return voteWeight;
    }

    public void setVoteWeight(short voteWeight) {
        this.voteWeight = voteWeight;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public enum Response {

        YES("Yes"),
        NO("No"),
        UNKNOWN("Unsure"),
        NOT_IMPORTANT("This event isn't important")
        ;

        private final String text;

        Response(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
