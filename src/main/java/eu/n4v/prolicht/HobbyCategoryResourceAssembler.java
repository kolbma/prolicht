package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.CategoryView;
import eu.n4v.prolicht.model.HobbyCategory;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
@ApiIgnore
class HobbyCategoryResourceAssembler
        implements ResourceAssembler<HobbyCategory, Resource<CategoryView>> {

    @Override
    public Resource<CategoryView> toResource(HobbyCategory category) {
        CategoryView view = new CategoryView(category);
        return new Resource<>(view,
                linkTo(methodOn(HobbyCategoryController.class).one(category.getId())).withSelfRel(),
                linkTo(methodOn(HobbyCategoryController.class).all()).withRel("hobbycategories"));
    }
}
