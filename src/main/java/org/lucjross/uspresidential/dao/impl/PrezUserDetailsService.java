package org.lucjross.uspresidential.dao.impl;

import org.lucjross.uspresidential.dao.UserDAO;
import org.lucjross.uspresidential.model.PrezUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lucas on 1/11/16.
 */
@Service
public class PrezUserDetailsService extends JdbcDaoImpl implements UserDAO {

    private final NamedParameterJdbcOperations npJdbcOps;

    @Autowired
    PrezUserDetailsService(DataSource dataSource) {
        super();
        setUsersByUsernameQuery("select * from users where username = ?");
        setDataSource(dataSource);
        npJdbcOps = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final String CREATE_SQL =
            "insert into users (" +
            "username, password, enabled, email, " +
            "birthDate, gender, politicsSocial, politicsFiscal, " +
            "education, occupation, stateOrTerritory, countryAlpha2Code, " +
            "religion, annualIncome, maritalStatus, sexuality) " +
            "values (" +
            ":username, :password, :enabled, :email, " +
            ":birthDate, :gender, :politicsSocial, :politicsFiscal, " +
            ":education, :occupation, :stateOrTerritory, :countryAlpha2Code, " +
            ":religion, :annualIncome, :maritalStatus, :sexuality)";

    private static final String CREATE_AUTHORITY_SQL =
            "insert into authorities (username, authority) values (?, ?)";

    @Override
    public void createFromForm(PrezUser.Form userForm, PrezUser.Optionals userOptionalsForm) {

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("username", userForm.getUsername());

        String bcryptedPassword = BCrypt.hashpw(userForm.getPassword(), BCrypt.gensalt());
        userForm.setPassword(null);
        source.addValue("password", bcryptedPassword);
        source.addValue("enabled", true);
        source.addValue("email", userForm.getEmail());
        source.addValue("birthDate", userOptionalsForm.getBirthDate());
        source.addValue("gender", userOptionalsForm.getGender());
        source.addValue("politicsSocial", userOptionalsForm.getPoliticsSocial());
        source.addValue("politicsFiscal", userOptionalsForm.getPoliticsFiscal());
        source.addValue("education", userOptionalsForm.getEducation());
        source.addValue("occupation", userOptionalsForm.getOccupation());
        source.addValue("stateOrTerritory", userOptionalsForm.getStateOrTerritory());
        source.addValue("countryAlpha2Code", userOptionalsForm.getCountry());
        source.addValue("religion", userOptionalsForm.getReligion());
        source.addValue("annualIncome", userOptionalsForm.getAnnualIncome());
        source.addValue("maritalStatus", userOptionalsForm.getMaritalStatus());
        source.addValue("sexuality", userOptionalsForm.getSexuality());

        npJdbcOps.update(CREATE_SQL, source);

        getJdbcTemplate().update(CREATE_AUTHORITY_SQL,
                userForm.getUsername(), "ROLE_USER");
    }

    @Override
    public void create(PrezUser prezUser) {
        throw new UnsupportedOperationException();
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
    public int update(PrezUser prezUser) {
        throw new UnsupportedOperationException();
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
                    optionals.setBirthDate(rs.getDate("birthDate"));
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
