package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.Knowledge;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class KnowledgeResourceAssembler implements ResourceAssembler<Knowledge, Resource<Knowledge>> {

    @Override
    public Resource<Knowledge> toResource(Knowledge knowledge) {

        return new Resource<>(knowledge,
                linkTo(methodOn(KnowledgeController.class).one(knowledge.getCategoryId(),
                        knowledge.getId())).withSelfRel(),
                linkTo(methodOn(KnowledgeController.class).all()).withRel("knowledge"));
    }
}
