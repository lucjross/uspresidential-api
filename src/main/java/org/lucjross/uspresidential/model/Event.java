package org.lucjross.uspresidential.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by lucas on 11/23/2014.
 */
public class Event implements Serializable {
    static final long serialVersionUID = 1L;

    private int ID;
    private String description;
    private int president_id;
    private short weight;
    private String importance;
    private String category;
    private String summary;
    private Date start;
    private Date end;
    private String wiki_link;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPresident_id() {
        return president_id;
    }

    public void setPresident_id(int president_id) {
        this.president_id = president_id;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getWiki_link() {
        return wiki_link;
    }

    public void setWiki_link(String wiki_link) {
        this.wiki_link = wiki_link;
    }

}
