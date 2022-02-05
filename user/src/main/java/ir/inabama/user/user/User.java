package ir.inabama.user.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.inabama.user.role.Role;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    private String name;

    @NaturalId
    @NotBlank
    @Size(max = 100)
    private String username;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 100)
    @JsonIgnore
    private String password;

    private String address;

    @Size(max = 20)
    private String phone;

    @Size(max = 20)
    private String mobile;

    @Column(name = "national_code")
    @Size(max = 10)
    private String nationalCode;

    private boolean confirmed;

    @Column(name = "register_image_url")
    @Size(max = 20971520)
    private String registerImageUrl;

    @Column(name = "register_number")
    @Size(max = 10)
    private String registerNumber;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}