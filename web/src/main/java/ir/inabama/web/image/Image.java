package ir.inabama.web.image;

import ir.inabama.web.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "image")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date();

	@Column(nullable = false, unique = true)
	private String imageId;

	private String fileName;

	@Lob
	@Column(nullable = false)
	private byte[] imageBytes;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
