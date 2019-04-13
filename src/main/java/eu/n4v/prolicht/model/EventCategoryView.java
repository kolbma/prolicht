package eu.n4v.prolicht.model;

import eu.n4v.prolicht.EventCategoryName;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Data
@NoArgsConstructor
@ApiIgnore
public class EventCategoryView implements IEventCategory {
    private Long categoryId;
    private Long id;
    private EventCategoryName name;
    private int sequence;

    public EventCategoryView(IEventCategory category) {
        this.categoryId = category.getCategoryId();
        this.id = category.getId();
        this.name = category.getName();
        this.sequence = category.getSequence();
    }
}
