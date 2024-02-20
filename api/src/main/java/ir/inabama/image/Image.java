package ir.inabama.image;

import ir.inabama.product.Product;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image")
public class Image {

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
    @JoinColumn(name = "product_id")
    private Product product;

}
