/*
   Copyright 2019 Markus Kolb

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package eu.n4v.prolicht.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
import javax.persistence.Transient;
import org.springframework.hateoas.Identifiable;
import eu.n4v.prolicht.ApplicationContextProviderConfig;
import io.swagger.annotations.ApiModelProperty;

@Data
@Entity
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "sequence")})
@NamedQuery(name = "Knowledge.findAll",
        query = "select k from Knowledge k order by k.sequence desc")
@NamedQuery(name = "Knowledge.findByKnowledgeCategoryId",
        query = "select k from Knowledge k where k.knowledgeCategory.id = ?1 order by k.sequence desc")
public class Knowledge implements Identifiable<Long> {

    @Id
    @GeneratedValue
    @ApiModelProperty(readOnly = true, hidden = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @ApiModelProperty(readOnly = true, hidden = true)
    private KnowledgeCategory knowledgeCategory;

    private String name;
    private int sequence;

    @Transient
    @ApiModelProperty(required = true)
    private Long categoryId;

    @PrePersist
    @PreUpdate
    private void preCallback() throws Exception {
        // support setting category by id
        if (categoryId != null
                && (knowledgeCategory == null || categoryId != knowledgeCategory.getId())) {
            KnowledgeCategoryRepository cRepo =
                    (KnowledgeCategoryRepository) ApplicationContextProviderConfig.contextProvider()
                            .getApplicationContext().getBean("knowledgeCategoryRepository");
            Optional<KnowledgeCategory> c = cRepo.findById(categoryId);
            if (c.isPresent()) {
                knowledgeCategory = c.get();
            } else {
                throw new Exception("category not found");
            }
        }
    }

    @PostLoad
    private void postLoadCallback() {
        categoryId = knowledgeCategory.getId();
    }

}
