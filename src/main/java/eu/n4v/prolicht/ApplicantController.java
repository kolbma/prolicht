package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import eu.n4v.prolicht.model.Applicant;
import eu.n4v.prolicht.model.ApplicantRepository;
import eu.n4v.prolicht.model.ApplicantView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class ApplicantController {

    private final ApplicantRepository repository;

    private final ApplicantResourceAssembler assembler;

    ApplicantController(ApplicantRepository repository, ApplicantResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/applicant", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "all", authorizations = {@Authorization(value = "basicAuth")})
    Resources<Resource<ApplicantView>> all() {
        List<Resource<ApplicantView>> applicant = repository.findAll().stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(applicant,
                linkTo(methodOn(ApplicantController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/applicant", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "newApplicant", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newApplicant(@RequestBody Applicant newApplicant) throws URISyntaxException {
        Resource<ApplicantView> resource = assembler.toResource(repository.save(newApplicant));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping(value = "/applicant/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resource<ApplicantView> one(@PathVariable Long id) {
        if (id == 0) {
            // handles a request for specifying no (0 for dummy) id
            Applicant defaultApplicant = repository.findFirstByOrderByIdAsc()
                    .orElseThrow(() -> new ResNotFoundException(0L));
            return assembler.toResource(defaultApplicant);
        }
        Applicant applicant =
                repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(applicant);
    }

    @PutMapping(value = "/applicant/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "replaceApplicant",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceApplicant(@RequestBody Applicant newApplicant, @PathVariable Long id)
            throws URISyntaxException {
        Applicant updatedApplicant = repository.findById(id).map(applicant -> {
            applicant.setBirthdate(newApplicant.getBirthdate());
            applicant.setBirthplace(newApplicant.getBirthplace());
            applicant.setCity(newApplicant.getCity());
            applicant.setContactInfo(newApplicant.getContactInfo());
            applicant.setDrivinglicense(newApplicant.getDrivinglicense());
            applicant.setEmail(newApplicant.getEmail());
            applicant.setFirstname(newApplicant.getFirstname());
            applicant.setIntro(newApplicant.getIntro());
            applicant.setLastname(newApplicant.getLastname());
            applicant.setPhone(newApplicant.getPhone());
            applicant.setPostcode(newApplicant.getPostcode());
            applicant.setStreet(newApplicant.getStreet());
            applicant.setTitle(newApplicant.getTitle());
            return repository.save(applicant);
        }).orElseGet(() -> {
            newApplicant.setId(id);
            return repository.save(newApplicant);
        });
        Resource<ApplicantView> resource = assembler.toResource(updatedApplicant);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/applicant/{id}")
    @ApiOperation(value = "deleteApplicant", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteApplicant(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
