package ir.bigchiu.clothing.models.order;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class OrderItem {

    private String model;
    private String material;

}
