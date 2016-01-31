package org.lucjross.uspresidential.model;

import java.io.Serializable;

/**
 * Created by lucas on 11/23/2014.
 */
public class Event implements Serializable {

    static final long serialVersionUID = 1L;

    private int id;
    private String description;
    private int president_id;
    private short weight;
    private Importance importance;
    private Category category;
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

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return id == event.id;

    }

    @Override
    public int hashCode() {
        return id;
    }


    public enum Importance {
        MAJOR("major"),
        MINOR("minor")
        ;

        private final String text;

        Importance(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public enum Category {
        FOREIGN("foreign policy"),
        DOMESTIC("domestic policy"),
        APPOINTMENT("appointment"),
        MISC("miscellaneous");

        private final String text;

        Category(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
