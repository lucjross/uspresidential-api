package org.lucjross.uspresidential.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by lucas on 1/16/16.
 */
public class PrezUser extends User {

    protected final LocalDateTime creationTimestamp;
    protected final String email;
    protected final Optionals prezUserOptionals;

    public PrezUser(
            String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            LocalDateTime creationTimestamp,
            String email,
            Optionals prezUserOptionals) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.creationTimestamp = Objects.requireNonNull(creationTimestamp);
        this.email = Objects.requireNonNull(email);
        this.prezUserOptionals = Objects.requireNonNull(prezUserOptionals);
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public Optionals getPrezUserOptionals() {
        return prezUserOptionals;
    }

    public String getEmail() {
        return email;
    }


    public static class Form {

        private String username;
        private String password;
        private String email;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


    public static class Optionals {

        protected LocalDate birthDate;
        protected String gender;
        protected String politicsSocial;
        protected String politicsFiscal;
        protected String education;
        protected String occupation;
        protected String stateOrTerritory;
        protected String country;
        protected String religion;
        protected String annualIncome;
        protected String maritalStatus;
        protected String sexuality;

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPoliticsSocial() {
            return politicsSocial;
        }

        public void setPoliticsSocial(String politicsSocial) {
            this.politicsSocial = politicsSocial;
        }

        public String getPoliticsFiscal() {
            return politicsFiscal;
        }

        public void setPoliticsFiscal(String politicsFiscal) {
            this.politicsFiscal = politicsFiscal;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getStateOrTerritory() {
            return stateOrTerritory;
        }

        public void setStateOrTerritory(String stateOrTerritory) {
            this.stateOrTerritory = stateOrTerritory;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getAnnualIncome() {
            return annualIncome;
        }

        public void setAnnualIncome(String annualIncome) {
            this.annualIncome = annualIncome;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getSexuality() {
            return sexuality;
        }

        public void setSexuality(String sexuality) {
            this.sexuality = sexuality;
        }
    }
}
