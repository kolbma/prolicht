package eu.n4v.prolicht.model;

import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import org.springframework.hateoas.Identifiable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import eu.n4v.prolicht.ApplicationContextProviderConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "sequence")})
@NamedQuery(name = "KnowledgeCategory.findAll",
        query = "select k from KnowledgeCategory k order by k.sequence desc")
public class KnowledgeCategory implements Identifiable<Long> {

    @Id
    @GeneratedValue
    @ApiModelProperty(readOnly = true, hidden = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @ApiModelProperty(readOnly = true, hidden = true)
    private Applicant applicant;

    private String name;
    private int sequence;

    @PrePersist
    @PreUpdate
    private void preCallback() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (applicant == null || !username.equals(applicant.getAccount().getUsername())) {
            // assign to login user via applicant of login user
            AccountRepository accountRepo = (AccountRepository) ApplicationContextProviderConfig
                    .contextProvider().getApplicationContext().getBean("accountRepository");
            Optional<Account> a = accountRepo.findOneByUsername(username);
            if (a.isPresent()) {
                ApplicantRepository applicantRepo =
                        (ApplicantRepository) ApplicationContextProviderConfig.contextProvider()
                                .getApplicationContext().getBean("applicantRepository");
                Optional<Applicant> appli = applicantRepo.findOneByAccount(a.get());
                applicant = appli.get();
            } else {
                throw new Exception("account not found");
            }
        }

    }

}