package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.PrezUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by lucas on 1/11/16.
 */
@Service
public class PrezUserDetailsService extends JdbcDaoImpl {

    @Autowired
    PrezUserDetailsService(@SuppressWarnings("SpringJavaAutowiringInspection") DataSource dataSource) {
        super();
        setUsersByUsernameQuery("select * " +
                "from users " + "where username = ?");
        setDataSource(dataSource);
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] { username },
                (rs, rowNum) -> {
                    String username1 = rs.getString(1);
                    String password = rs.getString(2);
                    boolean enabled = rs.getBoolean(3);
                    LocalDateTime creationTimestamp = rs.getTimestamp("creationTimestamp").toLocalDateTime();
                    java.sql.Date rsBirthDate = rs.getDate("birthDate");
                    Optional<LocalDate> birthDate =
                            Optional.ofNullable(rsBirthDate == null ? null : rsBirthDate.toLocalDate());
                    Optional<String> gender = Optional.ofNullable(rs.getString("gender"));
                    Optional<String> politicsSocial = Optional.ofNullable(rs.getString("politicsSocial"));
                    Optional<String> politicsFiscal = Optional.ofNullable(rs.getString("politicsFiscal"));
                    Optional<String> education = Optional.ofNullable(rs.getString("education"));
                    Optional<String> occupation = Optional.ofNullable(rs.getString("occupation"));
                    Optional<String> stateOrTerritory = Optional.ofNullable(rs.getString("stateOrTerritory"));
                    Optional<String> country = Optional.ofNullable(rs.getString("countryAlpha2Code"));
                    Optional<String> religion = Optional.ofNullable(rs.getString("religion"));
                    Optional<String> annualIncome = Optional.ofNullable(rs.getString("annualIncome"));
                    Optional<String> maritalStatus = Optional.ofNullable(rs.getString("maritalStatus"));
                    Optional<String> sexuality = Optional.ofNullable(rs.getString("sexuality"));
                    return new PrezUser(username1, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES,
                            creationTimestamp,
                            birthDate,
                            gender,
                            politicsSocial,
                            politicsFiscal,
                            education,
                            occupation,
                            stateOrTerritory,
                            country,
                            religion,
                            annualIncome,
                            maritalStatus,
                            sexuality);
                });
    }

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        if (!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        PrezUser prezUserFromUserQuery = (PrezUser) userFromUserQuery;
        return new PrezUser(returnUsername, userFromUserQuery.getPassword(),
                userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities,
                prezUserFromUserQuery.getCreationTimestamp(),
                prezUserFromUserQuery.getBirthDate(),
                prezUserFromUserQuery.getGender(),
                prezUserFromUserQuery.getPoliticsSocial(),
                prezUserFromUserQuery.getPoliticsFiscal(),
                prezUserFromUserQuery.getEducation(),
                prezUserFromUserQuery.getOccupation(),
                prezUserFromUserQuery.getStateOrTerritory(),
                prezUserFromUserQuery.getCountry(),
                prezUserFromUserQuery.getReligion(),
                prezUserFromUserQuery.getAnnualIncome(),
                prezUserFromUserQuery.getMaritalStatus(),
                prezUserFromUserQuery.getSexuality());
    }
}
