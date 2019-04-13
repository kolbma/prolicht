package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.EventCategory;
import eu.n4v.prolicht.model.EventCategoryView;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
@ApiIgnore
class EventCategoryResourceAssembler
        implements ResourceAssembler<EventCategory, Resource<EventCategoryView>> {

    @Override
    public Resource<EventCategoryView> toResource(EventCategory category) {
        EventCategoryView view = new EventCategoryView(category);
        return new Resource<>(view,
                linkTo(methodOn(EventCategoryController.class).one(category.getId())).withSelfRel(),
                linkTo(methodOn(EventCategoryController.class).all()).withRel("eventcategories"));
    }
}
