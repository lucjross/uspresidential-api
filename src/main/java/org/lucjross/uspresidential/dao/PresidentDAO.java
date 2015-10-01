package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.President;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by lucas on 11/21/2014.
 */
public interface PresidentDAO extends DAO<President> {

    /**
     * @return All presidents in order
     */
    public List<President> getPresidents();
}
