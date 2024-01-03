package ir.inabama.web.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.inabama.web.image.Image;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date();

	private String name;

	private String category;

	private String color;

	private String material;

	private Double size;

	private String model;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private Set<Image> images = new HashSet<>();

}
