package eu.n4v.prolicht.model;

import eu.n4v.prolicht.EventCategoryName;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

@Data
@ApiIgnore
public class EventCategoryView implements IEventCategory {
    private Long id;
    private EventCategoryName name;
    private int sequence;

    public EventCategoryView(IEventCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        this.sequence = category.getSequence();
    }
}
