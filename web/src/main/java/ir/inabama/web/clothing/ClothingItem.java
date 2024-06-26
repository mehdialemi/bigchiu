package ir.inabama.web.clothing;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Embedded
    private ClothingItemStats stats;

}
