package eu.n4v.prolicht.model;

import java.util.Date;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import eu.n4v.prolicht.ApplicationContextProviderConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "account_id")})
public class Applicant implements IApplicant {

    @Id
    @GeneratedValue
    @ApiModelProperty(readOnly = true, hidden = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @ApiModelProperty(readOnly = false, hidden = true, reference = "Account")
    private Account account;

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
    @Lob
    private String intro;
    private String contactInfo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @ApiModelProperty(readOnly = false, hidden = true)
    private Date updatedat;

    @PrePersist
    @PreUpdate
    private void preCallback() throws Exception {
        updatedat = new Date();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (account == null || !username.equals(account.getUsername())) {
            // assign to login user
            AccountRepository accountRepo = (AccountRepository) ApplicationContextProviderConfig
                    .contextProvider().getApplicationContext().getBean("accountRepository");
            Optional<Account> a = accountRepo.findOneByUsername(username);
            if (a.isPresent()) {
                account = a.get();
            } else {
                throw new Exception("account not found");
            }
        }
    }

}
