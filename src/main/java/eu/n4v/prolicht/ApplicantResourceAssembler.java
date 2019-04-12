package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.Applicant;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class ApplicantResourceAssembler implements ResourceAssembler<Applicant, Resource<Applicant>> {

    @Override
    public Resource<Applicant> toResource(Applicant applicant) {

        return new Resource<>(applicant,
                linkTo(methodOn(ApplicantController.class).one(applicant.getId())).withSelfRel(),
                linkTo(methodOn(ApplicantController.class).all()).withRel("applicants"));
    }
}
