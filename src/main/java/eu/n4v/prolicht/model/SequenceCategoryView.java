package eu.n4v.prolicht.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Data
@NoArgsConstructor
@ApiIgnore
public class SequenceCategoryView implements ISequenceCategory {
    private Long categoryId;
    private Long id;
    private String name;
    private int sequence;

    public SequenceCategoryView(ISequenceCategory category) {
        this.categoryId = category.getCategoryId();
        this.id = category.getId();
        this.name = category.getName();
        this.sequence = category.getSequence();
    }
}
