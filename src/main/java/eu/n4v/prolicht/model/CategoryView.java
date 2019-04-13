package eu.n4v.prolicht.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Data
@NoArgsConstructor
@ApiIgnore
public class CategoryView implements ICategory {
    private Long categoryId;
    private Long id;
    private String name;

    public CategoryView(ICategory category) {
        this.id = category.getId();
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
    }
}
