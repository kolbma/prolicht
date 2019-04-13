package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import eu.n4v.prolicht.model.Photo;
import eu.n4v.prolicht.model.PhotoView;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
@ApiIgnore
class PhotoResourceAssembler implements ResourceAssembler<Photo, Resource<PhotoView>> {

    @Override
    public Resource<PhotoView> toResource(Photo photo) {
        PhotoView view = new PhotoView(photo);
        return new Resource<>(view,
                linkTo(methodOn(PhotoController.class).one(photo.getApplicant().getId(),
                        photo.getId())).withSelfRel(),
                linkTo(methodOn(PhotoController.class).all(photo.getApplicant().getId()))
                        .withRel("photos"));
    }
}
