package ir.inabama.appliker.media;

import ir.inabama.appliker.account.Account;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Media {

	@Id
	private Long id;

	@Column(unique = true, nullable = false)
	private String mediaId;

	@Column
	private Integer likeCount;

	@Column
	private Integer commentCount;

	@Column
	private String caption;

	@Column
	private String mediaType;

	@Column
	private Set<String> likers = new HashSet <>();

	@Column
	private Set<String> comments = new HashSet <>();

	@ManyToOne
	private Account account;
}
