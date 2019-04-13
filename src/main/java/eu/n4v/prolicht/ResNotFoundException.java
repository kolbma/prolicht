package eu.n4v.prolicht;

public class ResNotFoundException extends RuntimeException {

    private static final String COULD_NOT_FIND_RESOURCE = "Could not find resource ";
    private static final long serialVersionUID = -276736103776391047L;

    public ResNotFoundException(Long id) {
        super(COULD_NOT_FIND_RESOURCE + id);
    }
}
