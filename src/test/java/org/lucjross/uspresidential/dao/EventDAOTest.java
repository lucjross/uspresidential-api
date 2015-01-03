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
        Event event = eventDAO.find(3);
        Assert.assertTrue(event.getDescription().contains("Refused"));
    }

    @Test
    public void testListByPresident() {
        President president = new President();
        president.setID(1);
        List<Event> gWashingtonEvents = eventDAO.getEvents(president);
        for (Event event : gWashingtonEvents)
        {
            Assert.assertTrue(event.getDescription().length() > 0);
            if (event.getEnd() != null)
            {
                Assert.assertTrue(event.getEnd().compareTo(Date.valueOf("1800-01-01")) < 0);
            }
        }
    }
}
