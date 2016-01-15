package org.lucjross.uspresidential.dao;

import org.lucjross.uspresidential.model.PrezUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        setUsersByUsernameQuery("select username,password,enabled, " +
                "creationTimestamp, " +
                "namePrefix, firstName, lastName, " +
                "birthDate, gender, politicsSocial, politicsFiscal, " +
                "education, occupation, stateOrTerritory, countryAlpha2Code, " +
                "religion, annualIncome, maritalStatus, sexuality " +
                "from users " + "where username = ?");
        setDataSource(dataSource);
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] { username },
                (RowMapper<UserDetails>) (rs, rowNum) -> {
                    String username1 = rs.getString(1);
                    String password = rs.getString(2);
                    boolean enabled = rs.getBoolean(3);
                    LocalDateTime creationTimestamp = rs.getTimestamp("creationTimestamp").toLocalDateTime();
                    java.sql.Date rsBirthDate = rs.getDate("birthDate");
                    Optional<LocalDate> birthDate =
                            Optional.ofNullable(rsBirthDate == null ? null : rsBirthDate.toLocalDate());
                    Optional<PrezUser.Gender> gender =
                            optionalFromRS(rs, "gender", PrezUser.Gender.class);
                    Optional<PrezUser.Politics> politicsSocial =
                            optionalFromRS(rs, "politicsSocial", PrezUser.Politics.class);
                    Optional<PrezUser.Politics> politicsFiscal =
                            optionalFromRS(rs, "politicsFiscal", PrezUser.Politics.class);
                    Optional<PrezUser.Education> education =
                            optionalFromRS(rs, "education", PrezUser.Education.class);
                    Optional<PrezUser.Occupation> occupation =
                            optionalFromRS(rs, "occupation", PrezUser.Occupation.class);
                    Optional<PrezUser.US> stateOrTerritory =
                            optionalFromRS(rs, "stateOrTerritory", PrezUser.US.class);
                    Optional<PrezUser.Country> country =
                            Optional.ofNullable(PrezUser.Country.parse(rs.getString("countryAlpha2Code")));
                    Optional<PrezUser.Religion> religion =
                            optionalFromRS(rs, "religion", PrezUser.Religion.class);
                    Optional<PrezUser.Income> annualIncome =
                            optionalFromRS(rs, "annualIncome", PrezUser.Income.class);
                    Optional<PrezUser.MaritalStatus> maritalStatus =
                            optionalFromRS(rs, "maritalStatus", PrezUser.MaritalStatus.class);
                    Optional<PrezUser.Sexuality> sexuality =
                            optionalFromRS(rs, "sexuality", PrezUser.Sexuality.class);
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

    private static <O extends Enum<O> & PrezUser.Labelled> Optional<O> optionalFromRS(
            ResultSet rs, String field, Class<O> enumClass) {
        try {
            String value = rs.getString(field);
            if (value == null)
                return Optional.empty();
            else {
                O o = Enum.valueOf(enumClass, value);
                return Optional.of(o);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
