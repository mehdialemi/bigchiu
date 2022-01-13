package ir.bigchiu.clothing.entities;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class ClothingItemStats {

    private Integer viewCount;
    private Integer buyCount;

}
