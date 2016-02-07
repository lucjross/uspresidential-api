package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.PrezUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucas on 1/14/16.
 */
public interface UserDAO extends DAO<PrezUser> {

    @Transactional
    void createFromForm(PrezUser.Form userForm, PrezUser.Optionals userOptionalsForm);
}
