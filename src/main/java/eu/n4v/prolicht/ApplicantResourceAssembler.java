package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.Applicant;
import eu.n4v.prolicht.model.ApplicantView;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
@ApiIgnore
class ApplicantResourceAssembler implements ResourceAssembler<Applicant, Resource<ApplicantView>> {

    @Override
    public Resource<ApplicantView> toResource(Applicant applicant) {
        ApplicantView view = new ApplicantView(applicant);
        return new Resource<>(view,
                linkTo(methodOn(ApplicantController.class).one(applicant.getId())).withSelfRel(),
                linkTo(methodOn(ApplicantController.class).all()).withRel("applicants"));
    }
}
