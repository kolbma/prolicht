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
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
class HateosController {

    @GetMapping(value = "/", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resource<?> getApiIndex() {
        return new Resource<>(new Object(),
                linkTo(methodOn(ApplicantController.class).all()).withRel("applicants"),
                linkTo(methodOn(ApplicantController.class).one(null)).withRel("applicant"),

                linkTo(methodOn(EventCategoryController.class).all()).withRel("eventcategories"),
                linkTo(methodOn(EventCategoryController.class).one(null)).withRel("eventcategory"),

                linkTo(methodOn(EventController.class).all()).withRel("events"),
                linkTo(methodOn(EventController.class).allByCategory(null))
                        .withRel("eventsByCategory"),
                linkTo(methodOn(EventController.class).one(null, null)).withRel("event"),

                linkTo(methodOn(HobbyCategoryController.class).all()).withRel("hobbycategories"),
                linkTo(methodOn(HobbyCategoryController.class).one(null)).withRel("hobbycategory"),

                linkTo(methodOn(HobbyController.class).all()).withRel("hobbies"),
                linkTo(methodOn(HobbyController.class).allByCategory(null))
                        .withRel("hobbiesByCategory"),
                linkTo(methodOn(HobbyController.class).one(null, null)).withRel("hobby"),

                linkTo(methodOn(KnowledgeCategoryController.class).all())
                        .withRel("knowledgecategories"),
                linkTo(methodOn(KnowledgeCategoryController.class).one(null))
                        .withRel("knowledgecategory"),

                linkTo(methodOn(KnowledgeController.class).all()).withRel("knowledges"),
                linkTo(methodOn(KnowledgeController.class).allByCategory(null))
                        .withRel("knowledgesByCategory"),
                linkTo(methodOn(KnowledgeController.class).one(null, null)).withRel("knowledge"),

                linkTo(methodOn(PhotoController.class).all(null)).withRel("photos"),
                linkTo(methodOn(PhotoController.class).one(null, null)).withRel("photo"),
                linkTo(methodOn(PhotoController.class).download(null)).withRel("photoDownload"),

                linkTo(methodOn(HateosController.class).getApiIndex()).withSelfRel());
    }
}
