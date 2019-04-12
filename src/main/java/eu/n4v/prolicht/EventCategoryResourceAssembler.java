package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.EventCategory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class EventCategoryResourceAssembler
        implements ResourceAssembler<EventCategory, Resource<EventCategory>> {

    @Override
    public Resource<EventCategory> toResource(EventCategory category) {

        return new Resource<>(category,
                linkTo(methodOn(EventCategoryController.class).one(category.getId())).withSelfRel(),
                linkTo(methodOn(EventCategoryController.class).all()).withRel("eventcategories"));
    }
}
