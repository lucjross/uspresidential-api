package org.lucjross.uspresidential.dao;

import org.junit.Assert;
import org.junit.Test;
import org.lucjross.uspresidential.TestCase;
import org.lucjross.uspresidential.model.Event;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
public class EventDAOTest extends TestCase {

    @Autowired
    private EventDAO eventDAO;

    @Test
    public void testGet() {
        Event event = eventDAO.find(1);
        Assert.assertTrue(event.getDescription().equals("desc1"));
    }

    @Test
    public void testListByPresident() {
        President president = new President();
        president.setID(1);
        List<Event> guy1Events = eventDAO.getEvents(president);
        Assert.assertEquals("desc1", guy1Events.get(0).getDescription());
        Assert.assertEquals(1, guy1Events.get(0).getPresident_id());
        Assert.assertEquals(10, guy1Events.get(0).getWeight());
        Assert.assertEquals("major", guy1Events.get(0).getImportance());
        Assert.assertEquals("foreign", guy1Events.get(0).getCategory());
        Assert.assertEquals("summary1", guy1Events.get(0).getSummary());
        Assert.assertEquals(Date.valueOf("1970-1-1"), guy1Events.get(0).getStart());
        Assert.assertEquals(Date.valueOf("1970-1-2"), guy1Events.get(0).getEnd());
        Assert.assertEquals("url1", guy1Events.get(0).getWiki_link());

    }
}
