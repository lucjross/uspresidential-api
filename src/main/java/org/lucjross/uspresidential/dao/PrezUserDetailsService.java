package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.PrezUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by lucas on 1/11/16.
 */
@Service
public class PrezUserDetailsService extends JdbcDaoImpl implements UserDAO {

    @Autowired
    PrezUserDetailsService(DataSource dataSource) {
        super();
        setUsersByUsernameQuery("select * from users where username = ?");
        setDataSource(dataSource);
    }

    @Transactional
    @Override
    public void create(PrezUser prezUser) {

    }

    @Override
    public PrezUser find(String username) {
        List<UserDetails> users = loadUsersByUsername(username);
        if (users.size() != 1) {
            throw new IncorrectResultSizeDataAccessException(1);
        }

        PrezUser u = (PrezUser) users.get(0);
        u.eraseCredentials();
        return u;
    }

    @Override
    public PrezUser update(PrezUser prezUser) {
        return null;
    }

    @Override
    public void delete(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] { username },
                (rs, rowNum) -> {
                    // non-nulls
                    String password = rs.getString(2);
                    boolean enabled = rs.getBoolean(3);
                    LocalDateTime creationTimestamp = rs.getTimestamp("creationTimestamp").toLocalDateTime();
                    String email = rs.getString("email");

                    // nullables
                    PrezUser.Optionals optionals = new PrezUser.Optionals();
                    java.sql.Date rsBirthDate = rs.getDate("birthDate");
                    optionals.setBirthDate(
                            rsBirthDate == null ? null : rsBirthDate.toLocalDate());
                    optionals.setGender(rs.getString("gender"));
                    optionals.setPoliticsSocial(rs.getString("politicsSocial"));
                    optionals.setPoliticsFiscal(rs.getString("politicsFiscal"));
                    optionals.setEducation(rs.getString("education"));
                    optionals.setOccupation(rs.getString("occupation"));
                    optionals.setStateOrTerritory(rs.getString("stateOrTerritory"));
                    optionals.setCountry(rs.getString("countryAlpha2Code"));
                    optionals.setReligion(rs.getString("religion"));
                    optionals.setAnnualIncome(rs.getString("annualIncome"));
                    optionals.setMaritalStatus(rs.getString("maritalStatus"));
                    optionals.setSexuality(rs.getString("sexuality"));

                    return new PrezUser(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES,
                            creationTimestamp, email, optionals);
                });
    }

    @Override
    protected UserDetails createUserDetails(
            String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {

        PrezUser prezUserFromUserQuery = (PrezUser) userFromUserQuery;
        return new PrezUser(userFromUserQuery.getUsername(), userFromUserQuery.getPassword(),
                userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities,
                prezUserFromUserQuery.getCreationTimestamp(),
                prezUserFromUserQuery.getEmail(),
                prezUserFromUserQuery.getPrezUserOptionals());
    }
}
