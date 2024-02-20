package ir.inabama.product;

import ir.inabama.category.Category;
import ir.inabama.image.Image;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private String description;

    @ElementCollection
    private Set<ProductAttribute> mainFeatures;

    @ElementCollection
    private Set<ProductAttribute> details;

    @OneToMany(mappedBy = "product")
    private Set<Image> images = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(
//            name = "product_category",
//            joinColumns = {
//                    @JoinColumn(name = "product_id")
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "category_id")
//            }
//    )
//    private Set<Category> categories = new HashSet<>();

}
