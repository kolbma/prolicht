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
import eu.n4v.prolicht.model.Event;
import eu.n4v.prolicht.model.EventRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
class EventController {
    // TODO: query by account/applicant
    private final EventRepository repository;
    private final EventResourceAssembler assembler;

    EventController(EventRepository repository, EventResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/event", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resources<Resource<Event>> all() {
        List<Resource<Event>> event = repository.findAll().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(event, linkTo(methodOn(EventController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/event/{categoryId}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resources<Resource<Event>> allByCategory(@PathVariable Long categoryId) {
        List<Resource<Event>> event = repository.findByEventCategoryId(categoryId).stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(event,
                linkTo(methodOn(EventController.class).allByCategory(categoryId)).withSelfRel());
    }

    @PostMapping(value = "/event", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "newEvent", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newEvent(@RequestBody Event newEvent) throws URISyntaxException {
        Resource<Event> resource = assembler.toResource(repository.save(newEvent));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @GetMapping(value = "/event/{categoryId}/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resource<Event> one(@PathVariable Long categoryId, @PathVariable Long id) {
        Event event = repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(event);
    }

    @PutMapping(value = "/event/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "replaceEvent", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> replaceEvent(@RequestBody Event newEvent, @PathVariable Long id)
            throws URISyntaxException {
        Event updatedEvent = repository.findById(id).map(event -> {
            event.setCategoryId(newEvent.getCategoryId());
            event.setDateresolution(newEvent.getDateresolution());
            event.setDescription(newEvent.getDescription());
            event.setEnddate(newEvent.getEnddate());
            event.setStartdate(newEvent.getStartdate());
            event.setTitle(newEvent.getTitle());
            return repository.save(event);
        }).orElseGet(() -> {
            // newEvent.setId(id); // commented because of a bug in Hibernate
            return repository.save(newEvent);
        });
        Resource<Event> resource = assembler.toResource(updatedEvent);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/event/{id}")
    @ApiOperation(value = "deleteEvent", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
