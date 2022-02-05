package ir.inabama.clothing.entities;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class ImageUrl {

    private String smallUrl;
    private String mediumUrl;
    private String largeUrl;

}

