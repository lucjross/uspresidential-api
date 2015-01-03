package org.lucjross.uspresidential.model;

import java.io.Serializable;

/**
 * Created by lucas on 11/23/2014.
 */
public class President implements Serializable {
    static final long serialVersionUID = 1L;

    private int ID;
    private String lastname;
    private String firstname;
    private int order;
    private String wiki_link;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getWiki_link() {
        return wiki_link;
    }

    public void setWiki_link(String wiki_link) {
        this.wiki_link = wiki_link;
    }

    @Override
    public String toString() {
        return "President{" +
                "ID=" + ID +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", order=" + order +
                ", wiki_link='" + wiki_link + '\'' +
                '}';
    }
}
