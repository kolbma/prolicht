package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
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
import eu.n4v.prolicht.model.Hobby;
import eu.n4v.prolicht.model.HobbyRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class HobbyController {
    // TODO: query by account/applicant
    private final HobbyRepository repository;
    private final HobbyResourceAssembler assembler;

    HobbyController(HobbyRepository repository, HobbyResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/hobby")
    Resources<Resource<Hobby>> all() {
        List<Resource<Hobby>> hobby = repository.findAll().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(hobby, linkTo(methodOn(HobbyController.class).all()).withSelfRel());
    }

    @GetMapping("/hobby/{categoryId}")
    Resources<Resource<Hobby>> allByCategory(@PathVariable Long categoryId) {
        List<Resource<Hobby>> hobby = repository.findByHobbyCategoryId(categoryId).stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(hobby,
                linkTo(methodOn(HobbyController.class).allByCategory(categoryId)).withSelfRel());
    }

    @PostMapping("/hobby")
    @ApiOperation(value = "newHobby", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newHobby(@RequestBody Hobby newHobby) throws URISyntaxException {
        Resource<Hobby> resource = assembler.toResource(repository.save(newHobby));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping("/hobby/{categoryId}/{id}")
    Resource<Hobby> one(@PathVariable Long categoryId, @PathVariable Long id) {
        Hobby hobby = repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(hobby);
    }

    @PutMapping("/hobby/{id}")
    @ApiOperation(value = "replaceHobby", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceHobby(@RequestBody Hobby newHobby, @PathVariable Long id)
            throws URISyntaxException {
        Hobby updatedHobby = repository.findById(id).map(hobby -> {
            hobby.setCategoryId(newHobby.getCategoryId());
            hobby.setName(newHobby.getName());
            return repository.save(hobby);
        }).orElseGet(() -> {
            // newHobby.setId(id); // commented because of a bug in Hibernate
            return repository.save(newHobby);
        });
        Resource<Hobby> resource = assembler.toResource(updatedHobby);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/hobby/{id}")
    @ApiOperation(value = "deleteHobby", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteHobby(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}