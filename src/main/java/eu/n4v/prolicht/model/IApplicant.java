package eu.n4v.prolicht.model;

import java.util.Date;

public interface IApplicant {
    public Long getId();

    public String getFirstname();

    public String getLastname();

    public String getStreet();

    public String getPostcode();

    public String getCity();

    public String getPhone();

    public String getEmail();

    public Date getBirthdate();

    public String getBirthplace();

    public String getDrivinglicense();

    public String getTitle();

    public String getIntro();

    public String getContactInfo();

    public Date getUpdatedat();

}
