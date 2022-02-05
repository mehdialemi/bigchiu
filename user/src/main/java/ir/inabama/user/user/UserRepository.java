package ir.inabama.user.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT * " +
            "FROM users AS u " +
            "INNER JOIN user_roles AS ur ON ur.user_id=u.id " +
            "INNER JOIN roles AS r ON r.id=ur.role_id AND r.name IN (:roles)"
            , countQuery = "SELECT * " +
            "FROM users AS u " +
            "INNER JOIN user_roles AS ur ON ur.user_id=u.id " +
            "INNER JOIN roles AS r ON r.id=ur.role_id AND r.name IN (:roles)"
            , nativeQuery = true)
	Page<User> findAllByFilterRoles(@Param("roles") List <String> roles, Pageable pageable);
}

