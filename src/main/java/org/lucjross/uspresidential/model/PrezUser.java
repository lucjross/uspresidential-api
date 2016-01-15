package org.lucjross.uspresidential.model;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by lucas on 1/12/16.
 */
public class PrezUser extends User {

    protected LocalDateTime creationTimestamp;
    protected Optional<LocalDate> birthDate;
    protected Optional<Gender> gender;
    protected Optional<Politics> politicsSocial;
    protected Optional<Politics> politicsFiscal;
    protected Optional<Education> education;
    protected Optional<Occupation> occupation;
    protected Optional<US> stateOrTerritory;
    protected Optional<Country> country;
    protected Optional<Religion> religion;
    protected Optional<Income> annualIncome;
    protected Optional<MaritalStatus> maritalStatus;
    protected Optional<Sexuality> sexuality;

    public PrezUser(
            String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            LocalDateTime creationTimestamp,
            Optional<LocalDate> birthDate,
            Optional<Gender> gender,
            Optional<Politics> politicsSocial,
            Optional<Politics> politicsFiscal,
            Optional<Education> education,
            Optional<Occupation> occupation,
            Optional<US> stateOrTerritory,
            Optional<Country> country,
            Optional<Religion> religion,
            Optional<Income> annualIncome,
            Optional<MaritalStatus> maritalStatus,
            Optional<Sexuality> sexuality) {
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

    public Optional<Gender> getGender() {
        return gender;
    }

    public Optional<Politics> getPoliticsSocial() {
        return politicsSocial;
    }

    public Optional<Politics> getPoliticsFiscal() {
        return politicsFiscal;
    }

    public Optional<Education> getEducation() {
        return education;
    }

    public Optional<Occupation> getOccupation() {
        return occupation;
    }

    public Optional<US> getStateOrTerritory() {
        return stateOrTerritory;
    }

    public Optional<Country> getCountry() {
        return country;
    }

    public Optional<Religion> getReligion() {
        return religion;
    }

    public Optional<Income> getAnnualIncome() {
        return annualIncome;
    }

    public Optional<MaritalStatus> getMaritalStatus() {
        return maritalStatus;
    }

    public Optional<Sexuality> getSexuality() {
        return sexuality;
    }


    // ---
    // ~ Enums
    // ---

    public interface Labelled {
        String getLabel();
        String name();
    }


    public enum Politics implements Labelled {
        CONSERVATIVE("Conservative"),
        MODERATE("Moderate"),
        LIBERAL("Liberal");

        private String displayName;

        Politics(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getLabel() {
            return displayName;
        }
    }

    public enum Gender implements Labelled {
        MALE("Male"),
        FEMALE("Female");

        private String displayName;

        Gender(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getLabel() {
            return displayName;
        }
    }

    public enum Education implements Labelled {
        LESS_THAN_HS("Less than HS"),
        HS_GRADUATE_OR_EQUIVALENT("HS Graduate or equivalent"),
        TECHNICAL_SCHOOL("Technical school"),
        SOME_COLLEGE("Some college"),
        TWO_YEAR_COLLEGE_ASSOC_DEGREE("Two-year college/Assoc degree"),
        FOUR_YEAR_COLLEGE_BACHELOR_DEGREE("Four-year college Bachelor degree"),
        MASTER_DEGREE("Master degree"),
        DOCTORATE("Doctorate");

        private String displayName;

        Education(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getLabel() {
            return displayName;
        }
    }

    public enum Occupation implements Labelled {

        ARCHITECTURE_AND_ENGINEERING("Architecture and Engineering"),
        ARTS__DESIGN__ENTERTAINMENT__SPORTS__AND_MEDIA("Arts, Design, Entertainment, Sports, and Media"),
        BUILDING_AND_GROUNDS_CLEANING_AND_MAINTENANCE("Building and Grounds Cleaning and Maintenance"),
        BUSINESS_AND_FINANCIAL_OPERATIONS("Business and Financial Operations"),
        COMMUNITY_AND_SOCIAL_SERVICE("Community and Social Service"),
        COMPUTER_AND_MATHEMATICAL("Computer and Mathematical"),
        CONSTRUCTION_AND_EXTRACTION("Construction and Extraction"),
        EDUCATION__TRAINING__AND_LIBRARY("Education, Training, and Library"),
        FARMING__FISHING__AND_FORESTRY("Farming, Fishing, and Forestry"),
        FOOD_PREPARATION_AND_SERVING("Food Preparation and Serving"),
        HEALTHCARE_PRACTITIONERS_AND_TECHNICAL("Healthcare Practitioners and Technical"),
        HEALTHCARE_SUPPORT("Healthcare Support"),
        INSTALLATION__MAINTENANCE__AND_REPAIR("Installation, Maintenance, and Repair"),
        LEGAL("Legal"),
        LIFE__PHYSICAL__AND_SOCIAL_SCIENCE("Life, Physical, and Social Science"),
        MANAGEMENT("Management"),
        MILITARY_SPECIFIC("Military Specific"),
        OFFICE_AND_ADMINISTRATIVE_SUPPORT("Office and Administrative Support"),
        PERSONAL_CARE_AND_SERVICE("Personal Care and Service"),
        PRODUCTION_MANUFACTURING("Production/Manufacturing"),
        PROTECTIVE_SERVICE("Protective Service"),
        SALES_AND_RELATED("Sales and Related"),
        TRANSPORTATION_AND_MATERIAL_MOVING("Transportation and Material Moving");

        private String displayName;

        Occupation(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getLabel() {
            return displayName;
        }
    }

    public enum Income implements Labelled {

        LOW(new IncomeRange(Optional.empty(), Optional.of(40))),
        MID_1(new IncomeRange(Optional.of(40), Optional.of(60))),
        MID_2(new IncomeRange(Optional.of(60), Optional.of(80))),
        MID_3(new IncomeRange(Optional.of(80), Optional.of(120))),
        HIGH(new IncomeRange(Optional.of(120), Optional.empty()));

        private IncomeRange incomeRange;

        Income(IncomeRange incomeRange) {
            this.incomeRange = incomeRange;
        }

        public IncomeRange getIncomeRange() {
            return incomeRange;
        }

        @Override
        public String getLabel() {
            return incomeRange.toString();
        }

        static class IncomeRange {

            private final Optional<Integer> low;
            private final Optional<Integer> high;

            /**
             * @param low  inclusive
             * @param high  exclusive
             */
            IncomeRange(Optional<Integer> low, Optional<Integer> high) {
                this.low = low;
                this.high = high;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                boolean lower = low.isPresent();
                boolean upper = high.isPresent();
                if (lower) {
                    if (! upper)
                        sb.append("> ");
                    sb.append(low.get()).append(upper ? " - " : "k");
                    if (upper)
                        sb.append(high.get() - 1).append("k"); // subtracting 1 to show exclusivity
                }
                else {
                    if (! upper)
                        return "?";
                    sb.append("< ").append(high.get()).append("k");
                }
                return sb.toString();
            }
        }
    }

    public enum US implements Labelled {

        ALABAMA("Alabama","AL","US-AL"),
        ALASKA("Alaska","AK","US-AK"),
        ARIZONA("Arizona","AZ","US-AZ"),
        ARKANSAS("Arkansas","AR","US-AR"),
        CALIFORNIA("California","CA","US-CA"),
        COLORADO("Colorado","CO","US-CO"),
        CONNECTICUT("Connecticut","CT","US-CT"),
        DELAWARE("Delaware","DE","US-DE"),
        DISTRICT_OF_COLUMBIA("District of Columbia","DC","US-DC"),
        FLORIDA("Florida","FL","US-FL"),
        GEORGIA("Georgia","GA","US-GA"),
        HAWAII("Hawaii","HI","US-HI"),
        IDAHO("Idaho","ID","US-ID"),
        ILLINOIS("Illinois","IL","US-IL"),
        INDIANA("Indiana","IN","US-IN"),
        IOWA("Iowa","IA","US-IA"),
        KANSAS("Kansas","KS","US-KS"),
        KENTUCKY("Kentucky","KY","US-KY"),
        LOUISIANA("Louisiana","LA","US-LA"),
        MAINE("Maine","ME","US-ME"),
        MARYLAND("Maryland","MD","US-MD"),
        MASSACHUSETTS("Massachusetts","MA","US-MA"),
        MICHIGAN("Michigan","MI","US-MI"),
        MINNESOTA("Minnesota","MN","US-MN"),
        MISSISSIPPI("Mississippi","MS","US-MS"),
        MISSOURI("Missouri","MO","US-MO"),
        MONTANA("Montana","MT","US-MT"),
        NEBRASKA("Nebraska","NE","US-NE"),
        NEVADA("Nevada","NV","US-NV"),
        NEW_HAMPSHIRE("New Hampshire","NH","US-NH"),
        NEW_JERSEY("New Jersey","NJ","US-NJ"),
        NEW_MEXICO("New Mexico","NM","US-NM"),
        NEW_YORK("New York","NY","US-NY"),
        NORTH_CAROLINA("North Carolina","NC","US-NC"),
        NORTH_DAKOTA("North Dakota","ND","US-ND"),
        OHIO("Ohio","OH","US-OH"),
        OKLAHOMA("Oklahoma","OK","US-OK"),
        OREGON("Oregon","OR","US-OR"),
        PENNSYLVANIA("Pennsylvania","PA","US-PA"),
        RHODE_ISLAND("Rhode Island","RI","US-RI"),
        SOUTH_CAROLINA("South Carolina","SC","US-SC"),
        SOUTH_DAKOTA("South Dakota","SD","US-SD"),
        TENNESSEE("Tennessee","TN","US-TN"),
        TEXAS("Texas","TX","US-TX"),
        UTAH("Utah","UT","US-UT"),
        VERMONT("Vermont","VT","US-VT"),
        VIRGINIA("Virginia","VA","US-VA"),
        WASHINGTON("Washington","WA","US-WA"),
        WEST_VIRGINIA("West Virginia","WV","US-WV"),
        WISCONSIN("Wisconsin","WI","US-WI"),
        WYOMING("Wyoming","WY","US-WY"),
        PUERTO_RICO("Puerto Rico","PR","US-PR");

        private String unnabreviated;
        private String ANSIabbreviation;
        private String ISOabbreviation;

        US(String unnabreviated, String ANSIabbreviation, String ISOabbreviation) {
            this.unnabreviated = unnabreviated;
            this.ANSIabbreviation = ANSIabbreviation;
            this.ISOabbreviation = ISOabbreviation;
        }

        public String getUnnabreviated() {
            return unnabreviated;
        }

        public String getANSIabbreviation() {
            return ANSIabbreviation;
        }

        public String getISOabbreviation() {
            return ISOabbreviation;
        }

        /**
         * Parse string input to enum. Accepts only abbreviated forms. Case insensitive.
         * @param input String to parse
         * @return The parsed US state, or null on failure.
         */
        public static US parse(String input) {
            if (null == input) {
                return null;
            }
            input = input.trim();
            for (US state : values()) {
                if (
                        state.ANSIabbreviation.equalsIgnoreCase(input) ||
                        state.ISOabbreviation.equalsIgnoreCase(input)) {
                    return state;
                }
            }
            return null;
        }

        @Override
        public String getLabel() {
            return unnabreviated;
        }
    }

    public static class Country implements Labelled {

        private com.neovisionaries.i18n.CountryCode countryCode;

        Country(CountryCode countryCode) {
            this.countryCode = Objects.requireNonNull(countryCode);
        }

        @Override
        public String getLabel() {
            return countryCode.getName();
        }

        @Override
        public String name() {
            return countryCode.name();
        }

        public static Country parse(String countryCodeAlpha) {
            CountryCode countryCode = CountryCode.getByCodeIgnoreCase(countryCodeAlpha);
            if (countryCode != null) {
                Country country = new Country(countryCode);
                return country;
            }
            return null;
        }
    }

    public enum Religion implements Labelled {

        CHRISTIANITY("Christianity"),
        ISLAM("Islam"),
        CATHOLICISM("Catholicism"),
        HINDUISM("Hinduism"),
        AGNOSTICISM("Agnosticism"),
        BUDDHISM("Buddhism"),
        ATHEISM("Atheism"),
        ANGLICANISM("Anglicanism"),
        SIKHISM("Sikhism"),
        SEVENTH_DAY_ADVENTISTS("Seventh-Day Adventists"),
        MORMONISM("Latter Day Saint Movement (Mormonism)"),
        JUDAISM("Judaism"),
        OTHER("Other");

        private String displayName;

        Religion(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getLabel() {
            return displayName;
        }
    }

    public enum MaritalStatus implements Labelled {
        NEVER_MARRIED("Never married"),
        MARRIED("Married"),
        DIVORCED("Divorced"),
        WIDOW_ER("Widow(er)"),
        DOMESTIC_PARTNERSHIP("Domestic partnership");

        private String displayName;

        MaritalStatus(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getLabel() {
            return displayName;
        }
    }

    public enum Sexuality implements Labelled {
        STRAIGHT("Straight or heterosexual"),
        LGBTQ("L/G/B/T/Q"),
        OTHER("Other");

        private String displayName;

        Sexuality(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String getLabel() {
            return displayName;
        }
    }
}
