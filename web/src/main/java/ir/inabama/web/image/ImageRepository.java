package ir.inabama.web.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Optional<Image> getByImageId(String imageId);
}
