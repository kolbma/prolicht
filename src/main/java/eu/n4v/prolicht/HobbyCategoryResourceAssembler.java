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
import eu.n4v.prolicht.model.CategoryView;
import eu.n4v.prolicht.model.HobbyCategory;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
@ApiIgnore
class HobbyCategoryResourceAssembler
        implements ResourceAssembler<HobbyCategory, Resource<CategoryView>> {

    @Override
    public Resource<CategoryView> toResource(HobbyCategory category) {
        CategoryView view = new CategoryView(category);
        return new Resource<>(view,
                linkTo(methodOn(HobbyCategoryController.class).one(category.getId())).withSelfRel(),
                linkTo(methodOn(HobbyCategoryController.class).all()).withRel("hobbycategories"));
    }
}
