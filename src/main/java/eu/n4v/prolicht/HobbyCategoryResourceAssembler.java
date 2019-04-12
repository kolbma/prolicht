package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.HobbyCategory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class HobbyCategoryResourceAssembler
        implements ResourceAssembler<HobbyCategory, Resource<HobbyCategory>> {

    @Override
    public Resource<HobbyCategory> toResource(HobbyCategory category) {

        return new Resource<>(category,
                linkTo(methodOn(HobbyCategoryController.class).one(category.getId())).withSelfRel(),
                linkTo(methodOn(HobbyCategoryController.class).all()).withRel("hobbycategories"));
    }
}
