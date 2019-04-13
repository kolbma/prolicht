package eu.n4v.prolicht.model;

import eu.n4v.prolicht.EventCategoryName;

public interface IEventCategory {
    public Long getCategoryId();
    public Long getId();
    public EventCategoryName getName();
    public int getSequence();
}
