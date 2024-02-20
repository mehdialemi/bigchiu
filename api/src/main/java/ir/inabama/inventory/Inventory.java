package ir.inabama.inventory;

import ir.inabama.product.Product;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class Inventory {

    @Id
    private Long id;

    private String name;
//
//    @OneToMany
//    private Set<Product> products;

}
