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
import eu.n4v.prolicht.model.Knowledge;
import eu.n4v.prolicht.model.KnowledgeRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class KnowledgeController {
    // TODO: query by account/applicant
    private final KnowledgeRepository repository;
    private final KnowledgeResourceAssembler assembler;

    KnowledgeController(KnowledgeRepository repository, KnowledgeResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/knowledge")
    Resources<Resource<Knowledge>> all() {
        List<Resource<Knowledge>> knowledge = repository.findAll().stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(knowledge,
                linkTo(methodOn(KnowledgeController.class).all()).withSelfRel());
    }

    @GetMapping("/knowledge/{categoryId}")
    Resources<Resource<Knowledge>> allByCategory(@PathVariable Long categoryId) {
        List<Resource<Knowledge>> knowledge = repository.findByKnowledgeCategoryId(categoryId)
                .stream().map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(knowledge,
                linkTo(methodOn(KnowledgeController.class).allByCategory(categoryId))
                        .withSelfRel());
    }

    @PostMapping("/knowledge")
    @ApiOperation(value = "newKnowledge", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newKnowledge(@RequestBody Knowledge newKnowledge) throws URISyntaxException {
        Resource<Knowledge> resource = assembler.toResource(repository.save(newKnowledge));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping("/knowledge/{categoryId}/{id}")
    Resource<Knowledge> one(@PathVariable Long categoryId, @PathVariable Long id) {
        Knowledge knowledge =
                repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(knowledge);
    }

    @PutMapping("/knowledge/{id}")
    @ApiOperation(value = "replaceKnowledge",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceKnowledge(@RequestBody Knowledge newKnowledge, @PathVariable Long id)
            throws URISyntaxException {
        Knowledge updatedCategory = repository.findById(id).map(knowledge -> {
            knowledge.setCategoryId(newKnowledge.getCategoryId());
            knowledge.setName(newKnowledge.getName());
            knowledge.setSequence(newKnowledge.getSequence());
            return repository.save(knowledge);
        }).orElseGet(() -> {
            // newKnowledge.setId(id); // commented because of a bug in Hibernate
            return repository.save(newKnowledge);
        });
        Resource<Knowledge> resource = assembler.toResource(updatedCategory);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/knowledge/{id}")
    @ApiOperation(value = "deleteKnowledge", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteKnowledge(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
