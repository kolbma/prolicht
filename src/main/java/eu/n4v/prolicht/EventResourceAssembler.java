package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.Event;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class EventResourceAssembler implements ResourceAssembler<Event, Resource<Event>> {

    @Override
    public Resource<Event> toResource(Event event) {

        return new Resource<>(event,
                linkTo(methodOn(EventController.class).one(event.getCategoryId(), event.getId()))
                        .withSelfRel(),
                linkTo(methodOn(EventController.class).all()).withRel("event"));
    }
}
