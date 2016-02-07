package org.lucjross.uspresidential.dao;

import org.junit.Assert;
import org.junit.Test;
import org.lucjross.uspresidential.TestCase;
import org.lucjross.uspresidential.dao.impl.PresidentDAOImpl;
import org.lucjross.uspresidential.model.President;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lucas on 11/23/2014.
 */
public class PresidentDAOTest extends TestCase {

    @Autowired
    private PresidentDAO presidentDAO;

    @Test
    public void testList() {
        List<President> presidents = presidentDAO.getPresidents();
        Assert.assertTrue(presidents.size() == 10);
    }

    @Test
    public void testGet() {
        President key = new President();
        key.setId(1);
        President p = presidentDAO.find(key);
        Assert.assertEquals("prez1", p.getLastname());
        Assert.assertEquals("mr1", p.getFirstname());
    }
}
