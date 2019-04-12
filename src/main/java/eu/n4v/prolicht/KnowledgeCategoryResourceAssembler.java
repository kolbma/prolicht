package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.KnowledgeCategory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class KnowledgeCategoryResourceAssembler
        implements ResourceAssembler<KnowledgeCategory, Resource<KnowledgeCategory>> {

    @Override
    public Resource<KnowledgeCategory> toResource(KnowledgeCategory category) {

        return new Resource<>(category,
                linkTo(methodOn(KnowledgeCategoryController.class).one(category.getId()))
                        .withSelfRel(),
                linkTo(methodOn(KnowledgeCategoryController.class).all())
                        .withRel("knowledgecategories"));
    }
}
