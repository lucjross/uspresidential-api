package org.lucjross.uspresidential.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by lucas on 1/11/16.
 */
@Service
public class PrezUserDetailsService extends JdbcDaoImpl {

    @Autowired
    PrezUserDetailsService(DataSource dataSource) {
        super();
//        usersByUsernameQuery = DEF_USERS_BY_USERNAME_QUERY; // todo- override
        setDataSource(dataSource);
    }


}
