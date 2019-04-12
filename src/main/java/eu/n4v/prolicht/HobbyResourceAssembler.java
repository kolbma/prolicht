package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.Hobby;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class HobbyResourceAssembler implements ResourceAssembler<Hobby, Resource<Hobby>> {

    @Override
    public Resource<Hobby> toResource(Hobby hobby) {

        return new Resource<>(hobby,
                linkTo(methodOn(HobbyController.class).one(hobby.getCategoryId(), hobby.getId()))
                        .withSelfRel(),
                linkTo(methodOn(HobbyController.class).all()).withRel("hobbies"));
    }
}
