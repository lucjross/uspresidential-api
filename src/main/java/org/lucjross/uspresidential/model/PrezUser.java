package org.lucjross.uspresidential.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by lucas on 1/12/16.
 */
public class PrezUser extends User {

    protected LocalDateTime creationTimestamp;
    protected Optional<LocalDate> birthDate;
    protected Optional<String> gender;
    protected Optional<String> politicsSocial;
    protected Optional<String> politicsFiscal;
    protected Optional<String> education;
    protected Optional<String> occupation;
    protected Optional<String> stateOrTerritory;
    protected Optional<String> country;
    protected Optional<String> religion;
    protected Optional<String> annualIncome;
    protected Optional<String> maritalStatus;
    protected Optional<String> sexuality;

    public PrezUser(
            String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            LocalDateTime creationTimestamp,
            Optional<LocalDate> birthDate,
            Optional<String> gender,
            Optional<String> politicsSocial,
            Optional<String> politicsFiscal,
            Optional<String> education,
            Optional<String> occupation,
            Optional<String> stateOrTerritory,
            Optional<String> country,
            Optional<String> religion,
            Optional<String> annualIncome,
            Optional<String> maritalStatus,
            Optional<String> sexuality) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.creationTimestamp = Objects.requireNonNull(creationTimestamp);
        this.birthDate = Objects.requireNonNull(birthDate);
        this.gender = Objects.requireNonNull(gender);
        this.politicsSocial = Objects.requireNonNull(politicsSocial);
        this.politicsFiscal = Objects.requireNonNull(politicsFiscal);
        this.education = Objects.requireNonNull(education);
        this.occupation = Objects.requireNonNull(occupation);
        this.stateOrTerritory = Objects.requireNonNull(stateOrTerritory);
        this.country = Objects.requireNonNull(country);
        this.religion = Objects.requireNonNull(religion);
        this.annualIncome = Objects.requireNonNull(annualIncome);
        this.maritalStatus = Objects.requireNonNull(maritalStatus);
        this.sexuality = Objects.requireNonNull(sexuality);
    }

    // ---
    // ~ Getters
    // ---

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public Optional<LocalDate> getBirthDate() {
        return birthDate;
    }

    public Optional<String> getGender() {
        return gender;
    }

    public Optional<String> getPoliticsSocial() {
        return politicsSocial;
    }

    public Optional<String> getPoliticsFiscal() {
        return politicsFiscal;
    }

    public Optional<String> getEducation() {
        return education;
    }

    public Optional<String> getOccupation() {
        return occupation;
    }

    public Optional<String> getStateOrTerritory() {
        return stateOrTerritory;
    }

    public Optional<String> getCountry() {
        return country;
    }

    public Optional<String> getReligion() {
        return religion;
    }

    public Optional<String> getAnnualIncome() {
        return annualIncome;
    }

    public Optional<String> getMaritalStatus() {
        return maritalStatus;
    }

    public Optional<String> getSexuality() {
        return sexuality;
    }
}
