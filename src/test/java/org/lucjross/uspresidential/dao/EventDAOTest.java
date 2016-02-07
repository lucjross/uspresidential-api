package org.lucjross.uspresidential.dao;

import org.junit.Assert;
import org.junit.Test;
import org.lucjross.uspresidential.TestCase;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.EventAndVote;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
public class EventDAOTest extends TestCase {

    @Autowired
    private EventDAO eventDAO;

    @Test
    public void testGet() {
        Event key = new Event();
        key.setId(1);
        Event e = eventDAO.find(key);
        Assert.assertEquals("desc0", e.getDescription());
        Assert.assertEquals(1, e.getPresident_id());
        Assert.assertEquals(5, e.getWeight());
        Assert.assertEquals(Event.Importance.MINOR, e.getImportance());
        Assert.assertEquals(Event.Category.FOREIGN, e.getCategory());
        Assert.assertEquals("summary0", e.getSummary());
        Assert.assertEquals(Date.valueOf("1950-1-1"), e.getStart());
        Assert.assertEquals(Date.valueOf("1950-2-2"), e.getEnd());
        Assert.assertEquals("http://wherever/0", e.getWiki_link());
    }

    @Test
    public void testListByPresident() {
        Collection<EventAndVote> guy1Events = eventDAO.getEvents(Integer.MAX_VALUE, 0, true, null, 1);
        Assert.assertEquals(10, guy1Events.size());

        Event e = guy1Events.iterator().next().getEvent();
        Assert.assertEquals("desc0", e.getDescription());
        Assert.assertEquals(1, e.getPresident_id());
        Assert.assertEquals(5, e.getWeight());
        Assert.assertEquals(Event.Importance.MINOR, e.getImportance());
        Assert.assertEquals(Event.Category.FOREIGN, e.getCategory());
        Assert.assertEquals("summary0", e.getSummary());
        Assert.assertEquals(Date.valueOf("1950-1-1"), e.getStart());
        Assert.assertEquals(Date.valueOf("1950-2-2"), e.getEnd());
        Assert.assertEquals("http://wherever/0", e.getWiki_link());

    }
}
