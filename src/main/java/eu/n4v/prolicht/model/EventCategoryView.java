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

package eu.n4v.prolicht.model;

import eu.n4v.prolicht.EventCategoryName;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Data
@NoArgsConstructor
@ApiIgnore
public class EventCategoryView implements IEventCategory {
    private Long categoryId;
    private Long id;
    private EventCategoryName name;
    private int sequence;

    public EventCategoryView(IEventCategory category) {
        this.categoryId = category.getCategoryId();
        this.id = category.getId();
        this.name = category.getName();
        this.sequence = category.getSequence();
    }
}
