package eu.n4v.prolicht.model;

import java.util.Date;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import eu.n4v.prolicht.ApplicationContextProviderConfig;
import eu.n4v.prolicht.DateResolution;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "startdate,enddate")})
@NamedQuery(name = "Event.findAll",
        query = "select e from Event e order by e.startdate desc, e.enddate desc")
@NamedQuery(name = "Event.findByEventCategoryId",
        query = "select e from Event e where e.eventCategory.id = ?1 order by e.startdate desc, e.enddate desc")
public class Event {

    @Id
    @GeneratedValue
    @ApiModelProperty(readOnly = true, hidden = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @ApiModelProperty(readOnly = true, hidden = true)
    private EventCategory eventCategory;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;

    @Enumerated(EnumType.STRING)
    private DateResolution dateresolution;

    private String title;
    private String description;

    @Transient
    @ApiModelProperty(required = true)
    private Long categoryId;

    @PrePersist
    @PreUpdate
    private void preCallback() throws Exception {
        // support setting category by id
        if (categoryId != null && (eventCategory == null || categoryId != eventCategory.getId())) {
            EventCategoryRepository cRepo =
                    (EventCategoryRepository) ApplicationContextProviderConfig.contextProvider()
                            .getApplicationContext().getBean("eventCategoryRepository");
            Optional<EventCategory> c = cRepo.findById(categoryId);
            if (c.isPresent()) {
                eventCategory = c.get();
            } else {
                throw new Exception("category not found");
            }
        }
    }

    @PostLoad
    private void postLoadCallback() {
        categoryId = eventCategory.getId();
    }

}
