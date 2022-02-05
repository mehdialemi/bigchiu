package ir.inabama.clothing.services;

import ir.inabama.clothing.entities.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
}
