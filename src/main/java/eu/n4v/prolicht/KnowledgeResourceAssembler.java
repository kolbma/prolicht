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
import eu.n4v.prolicht.model.Knowledge;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
@ApiIgnore
class KnowledgeResourceAssembler implements ResourceAssembler<Knowledge, Resource<Knowledge>> {

    @Override
    public Resource<Knowledge> toResource(Knowledge knowledge) {

        return new Resource<>(knowledge,
                linkTo(methodOn(KnowledgeController.class).one(knowledge.getCategoryId(),
                        knowledge.getId())).withSelfRel(),
                linkTo(methodOn(KnowledgeController.class).all()).withRel("knowledges"));
    }
}
