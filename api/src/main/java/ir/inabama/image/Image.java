package ir.inabama.image;

import ir.inabama.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "image")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String smallLink;

    @Column
    private String mediumLink;

    @Column
    private String largeLink;

    @ManyToOne
    private Product product;

}
