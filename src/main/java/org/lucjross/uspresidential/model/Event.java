package org.lucjross.uspresidential.model;

import java.io.Serializable;

/**
 * Created by lucas on 11/23/2014.
 */
public class Event implements Serializable {

    static final long serialVersionUID = 1L;

    public static final String TABLE = "p_events";

    private int id;
    private String description;
    private int president_id;
    private short weight;
    private String importance;
    private String category;
    private String summary;
    private java.sql.Date start;
    private java.sql.Date end;
    private String wiki_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public java.sql.Date getStart() {
        return start;
    }

    public void setStart(java.sql.Date start) {
        this.start = start;
    }

    public java.sql.Date getEnd() {
        return end;
    }

    public void setEnd(java.sql.Date end) {
        this.end = end;
    }

    public String getWiki_link() {
        return wiki_link;
    }

    public void setWiki_link(String wiki_link) {
        this.wiki_link = wiki_link;
    }

}
