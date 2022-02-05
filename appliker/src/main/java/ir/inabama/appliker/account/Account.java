package ir.inabama.appliker.account;

import ir.inabama.appliker.media.Media;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
public class Account {

	@Id
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	private String fullName;

	@Column(unique = true)
	private Long pk;

	@OneToMany
	@JoinTable
	private Set<Media> mediaSet = new HashSet <>();

	@Column
	private String proxy;
}
