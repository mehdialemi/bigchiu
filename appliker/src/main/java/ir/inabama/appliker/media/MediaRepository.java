package ir.inabama.appliker.media;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

	Optional<Media> findByMediaId(String mediaId);
}
