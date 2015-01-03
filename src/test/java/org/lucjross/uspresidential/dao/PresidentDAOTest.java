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
        List<President> presidents = presidentDAO.getPresidentList();
        Assert.assertTrue(presidents.size() > 40);
    }

    @Test
    public void testGet() {
        President obama = presidentDAO.getPresident(44);
        Assert.assertEquals(obama.getLastname(), "Obama");
        Assert.assertEquals(obama.getFirstname(), "Barack");
    }
}
