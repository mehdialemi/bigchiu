package ir.bigchiu.shop.clothing;

import ir.bigchiu.shop.common.ImageUrl;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class ClothingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String model;

    private String material;

    private Integer size;

    private String color;

    @Embedded
    private ImageUrl imageUrl;

}
