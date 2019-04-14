package eu.n4v.prolicht.model;

import java.util.Date;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

@Data
@ApiIgnore
public class ApplicantView implements IApplicant {
    private Long id;
    private String firstname;
    private String lastname;
    private String street;
    private String postcode;
    private String city;
    private String phone;
    private String email;
    private Date birthdate;
    private String birthplace;
    private String drivinglicense;
    private String title;
    private String intro;
    private String contactInfo;
    private Date updatedat;

    public ApplicantView(IApplicant applicant) {
        this.id = applicant.getId();
        this.firstname = applicant.getFirstname();
        this.lastname = applicant.getLastname();
        this.street = applicant.getStreet();
        this.postcode = applicant.getPostcode();
        this.city = applicant.getCity();
        this.phone = applicant.getPhone();
        this.email = applicant.getEmail();
        this.birthdate = applicant.getBirthdate();
        this.birthplace = applicant.getBirthplace();
        this.drivinglicense = applicant.getDrivinglicense();
        this.title = applicant.getTitle();
        this.intro = applicant.getIntro();
        this.contactInfo = applicant.getContactInfo();
        this.updatedat = applicant.getUpdatedat();
    }
}
