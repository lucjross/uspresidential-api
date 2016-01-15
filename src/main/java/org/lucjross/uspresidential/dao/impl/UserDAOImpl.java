package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.AbstractDAO;
import org.lucjross.uspresidential.dao.UserDAO;
import org.lucjross.uspresidential.model.PrezUser;
import org.springframework.stereotype.Repository;

/**
 * Created by lucas on 1/14/16.
 */
@Repository
public class UserDAOImpl extends AbstractDAO<PrezUser> implements UserDAO {

    @Override
    public void create(PrezUser newUser) {

    }

}
