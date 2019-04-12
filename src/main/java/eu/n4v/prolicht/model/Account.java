package eu.n4v.prolicht.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
// import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
// import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import io.swagger.annotations.ApiModelProperty;

@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    @ApiModelProperty(readOnly = true, hidden = false)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @ApiModelProperty(readOnly = true, hidden = true)
    private Date createdat;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @ApiModelProperty(readOnly = true, hidden = true)
    private Date updatedat;

    @PrePersist
    @PreUpdate
    private void preCallback() {
        updatedat = new Date();
        if (createdat == null) {
            createdat = updatedat;
        }
    }

}
