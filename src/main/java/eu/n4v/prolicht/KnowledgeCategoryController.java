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
import eu.n4v.prolicht.model.KnowledgeCategory;
import eu.n4v.prolicht.model.KnowledgeCategoryRepository;
import eu.n4v.prolicht.model.SequenceCategoryView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class KnowledgeCategoryController {
    // TODO: query by account/applicant
    private final KnowledgeCategoryRepository repository;

    private final KnowledgeCategoryResourceAssembler assembler;

    KnowledgeCategoryController(KnowledgeCategoryRepository repository,
            KnowledgeCategoryResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/knowledgecategories", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resources<Resource<SequenceCategoryView>> all() {
        List<Resource<SequenceCategoryView>> categories = repository.findAll().stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(categories,
                linkTo(methodOn(KnowledgeCategoryController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/knowledgecategories", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "newKnowledgeCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newKnowledgeCategory(@RequestBody KnowledgeCategory newCategory)
            throws URISyntaxException {
        Resource<SequenceCategoryView> resource =
                assembler.toResource(repository.save(newCategory));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping(value = "/knowledgecategories/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resource<SequenceCategoryView> one(@PathVariable Long id) {
        KnowledgeCategory category =
                repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(category);
    }

    @PutMapping(value = "/knowledgecategories/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "replaceKnowledgeCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceKnowledgeCategory(@RequestBody KnowledgeCategory newCategory,
            @PathVariable Long id) throws URISyntaxException {
        KnowledgeCategory updatedCategory = repository.findById(id).map(category -> {
            category.setName(newCategory.getName());
            category.setSequence(newCategory.getSequence());
            return repository.save(category);
        }).orElseGet(() -> {
            newCategory.setId(id);
            return repository.save(newCategory);
        });
        Resource<SequenceCategoryView> resource = assembler.toResource(updatedCategory);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/knowledgecategories/{id}")
    @ApiOperation(value = "deleteKnowledgeCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteKnowledgeCategory(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
