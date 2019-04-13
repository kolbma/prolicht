package eu.n4v.prolicht.model;

import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

@Data
@ApiIgnore
public class SequenceCategoryView implements ISequenceCategory {
    private Long id;
    private String name;
    private int sequence;

    public SequenceCategoryView(ISequenceCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        this.sequence = category.getSequence();
    }
}
