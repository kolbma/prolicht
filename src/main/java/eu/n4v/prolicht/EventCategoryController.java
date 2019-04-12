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
import eu.n4v.prolicht.model.EventCategory;
import eu.n4v.prolicht.model.EventCategoryRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class EventCategoryController {
    // TODO: query by account/applicant
    private final EventCategoryRepository repository;

    private final EventCategoryResourceAssembler assembler;

    EventCategoryController(EventCategoryRepository repository,
            EventCategoryResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/eventcategories")
    Resources<Resource<EventCategory>> all() {
        List<Resource<EventCategory>> categories = repository.findAll().stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(categories,
                linkTo(methodOn(EventCategoryController.class).all()).withSelfRel());
    }

    @PostMapping("/eventcategories")
    @ApiOperation(value = "newEventCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newEventCategory(@RequestBody EventCategory newCategory)
            throws URISyntaxException {
        Resource<EventCategory> resource = assembler.toResource(repository.save(newCategory));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping("/eventcategories/{id}")
    Resource<EventCategory> one(@PathVariable Long id) {
        EventCategory category =
                repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(category);
    }

    @PutMapping("/eventcategories/{id}")
    @ApiOperation(value = "replaceEventCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceEventCategory(@RequestBody EventCategory newCategory,
            @PathVariable Long id) throws URISyntaxException {
        EventCategory updatedCategory = repository.findById(id).map(category -> {
            category.setName(newCategory.getName());
            return repository.save(category);
        }).orElseGet(() -> {
            newCategory.setId(id);
            return repository.save(newCategory);
        });
        Resource<EventCategory> resource = assembler.toResource(updatedCategory);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/eventcategories/{id}")
    @ApiOperation(value = "deleteEventCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteEventCategory(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
