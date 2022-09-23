package ir.inabama.web.clothing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
}
