package eu.n4v.prolicht.model;

import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

@Data
@ApiIgnore
public class PhotoView {
    private Long id;
    private int dataSize;
    private String filename;
    private String mediaType;

    public PhotoView(Photo photo) {
        this.id = photo.getId();
        this.dataSize = photo.getData().length;
        this.filename = photo.getFilename();
        this.mediaType = photo.getMediaType();
    }
}
