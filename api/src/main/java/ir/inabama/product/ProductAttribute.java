package ir.inabama.product;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class ProductAttribute {

    private String name;
    private String value;
}
