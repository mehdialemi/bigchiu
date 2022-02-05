package ir.inabama.appliker.media;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class MediaLocation {
	@Id
	private Long id;
	private Long pk;
	private String external_id;
	private String name;
	private String external_source;
	private Double lat;
	private Double lng;
	private String address;
	private Integer minimum_age;
}
