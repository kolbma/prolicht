/*
   Copyright 2019 Markus Kolb

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
import eu.n4v.prolicht.model.EventCategory;
import eu.n4v.prolicht.model.EventCategoryRepository;
import eu.n4v.prolicht.model.EventCategoryView;
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

    @GetMapping(value = "/eventcategories", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resources<Resource<EventCategoryView>> all() {
        List<Resource<EventCategoryView>> categories = repository.findAll().stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(categories,
                linkTo(methodOn(EventCategoryController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/eventcategories", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "newEventCategory",
            authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newEventCategory(@RequestBody EventCategory newCategory)
            throws URISyntaxException {
        Resource<EventCategoryView> resource = assembler.toResource(repository.save(newCategory));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping(value = "/eventcategories/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resource<EventCategoryView> one(@PathVariable Long id) {
        EventCategory category =
                repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(category);
    }

    @PutMapping(value = "/eventcategories/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
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
        Resource<EventCategoryView> resource = assembler.toResource(updatedCategory);
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
