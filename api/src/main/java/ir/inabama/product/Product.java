package ir.inabama.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import ir.inabama.category.Category;
import ir.inabama.image.Image;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductAttribute> attributes = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Image> images = new HashSet<>();


    @ManyToOne
    private Category category;
}
