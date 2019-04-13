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
import eu.n4v.prolicht.model.CategoryView;
import eu.n4v.prolicht.model.HobbyCategory;
import eu.n4v.prolicht.model.HobbyCategoryRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class HobbyCategoryController {
    // TODO: query by account/applicant
    private final HobbyCategoryRepository repository;

    private final HobbyCategoryResourceAssembler assembler;

    HobbyCategoryController(HobbyCategoryRepository repository,
            HobbyCategoryResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/hobbycategories", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resources<Resource<CategoryView>> all() {
        List<Resource<CategoryView>> categories = repository.findAll().stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(categories,
                linkTo(methodOn(HobbyCategoryController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/hobbycategories", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "newHobbyCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newHobbyCategory(@RequestBody HobbyCategory newCategory)
            throws URISyntaxException {
        Resource<CategoryView> resource = assembler.toResource(repository.save(newCategory));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping(value = "/hobbycategories/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resource<CategoryView> one(@PathVariable Long id) {
        HobbyCategory category =
                repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(category);
    }

    @PutMapping(value = "/hobbycategories/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "replaceHobbyCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceHobbyCategory(@RequestBody HobbyCategory newCategory,
            @PathVariable Long id) throws URISyntaxException {
        HobbyCategory updatedCategory = repository.findById(id).map(category -> {
            category.setName(newCategory.getName());
            return repository.save(category);
        }).orElseGet(() -> {
            newCategory.setId(id);
            return repository.save(newCategory);
        });
        Resource<CategoryView> resource = assembler.toResource(updatedCategory);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/hobbycategories/{id}")
    @ApiOperation(value = "deleteHobbyCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteHobbyCategory(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
