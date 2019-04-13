package eu.n4v.prolicht;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import eu.n4v.prolicht.model.Photo;
import eu.n4v.prolicht.model.PhotoRepository;
import eu.n4v.prolicht.model.PhotoView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class PhotoController {
    private final PhotoRepository repository;
    private final PhotoResourceAssembler assembler;

    PhotoController(PhotoRepository repository, PhotoResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/photo/{applicantId}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resources<Resource<PhotoView>> all(@PathVariable Long applicantId) {
        List<Resource<PhotoView>> photo = repository.findByApplicantId(applicantId).stream()
                .map(assembler::toResource).collect(Collectors.toList());
        return new Resources<>(photo,
                linkTo(methodOn(PhotoController.class).all(applicantId)).withSelfRel());
    }

    @GetMapping(value = "/photo/download/{id}")
    ResponseEntity<?> download(@PathVariable Long id) {
        Photo photo = repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        String mediaType = photo.getMediaType();
        if (mediaType == null) {
            mediaType = photo.getFilename();
            if (mediaType != null && mediaType.length() > 4) {
                String ext = mediaType.substring(mediaType.length() - 4);
                switch (ext) {
                    case ".jpg":
                        mediaType = MediaType.IMAGE_JPEG_VALUE;
                        break;
                    case ".png":
                        mediaType = MediaType.IMAGE_PNG_VALUE;
                        break;
                    case ".svg":
                        mediaType = "image/svg+xml";
                        break;
                    case ".gif":
                        mediaType = MediaType.IMAGE_GIF_VALUE;
                        break;
                    default:
                        mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                        break;
                }
            } else {
                mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, mediaType)
                .body(photo.getData());
    }

    @PostMapping(value = "/photo", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @ApiOperation(value = "newPhoto", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> newPhoto(@RequestParam("data") MultipartFile data) throws URISyntaxException {
        Photo newPhoto = new Photo();
        try {
            newPhoto.setData(data.getBytes());
            newPhoto.setMediaType(data.getContentType());
            String[] tokens = data.getOriginalFilename().split("[\\|/]");
            String filename = tokens[tokens.length - 1];
            newPhoto.setFilename(filename);
            Resource<PhotoView> resource = assembler.toResource(repository.save(newPhoto));
            return ResponseEntity.created(new URI(resource.getId().expand().getHref())).build();
        } catch (IOException ex) {
            log.error("failed to receive photo data");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/photo/{applicantId}/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    Resource<PhotoView> one(@PathVariable Long applicantId, @PathVariable Long id) {
        Photo photo = repository.findById(id).orElseThrow(() -> new ResNotFoundException(id));
        return assembler.toResource(photo);
    }

    @DeleteMapping("/photo/{id}")
    @ApiOperation(value = "deletePhoto", authorizations = {@Authorization(value = "basicAuth")})
    ResponseEntity<?> deletePhoto(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
