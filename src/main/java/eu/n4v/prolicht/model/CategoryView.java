package eu.n4v.prolicht.model;

import lombok.Data;

@Data
public class CategoryView implements ICategory {
    private Long id;
    private String name;

    public CategoryView(ICategory category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
