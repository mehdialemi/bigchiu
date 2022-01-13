package ir.bigchiu.clothing.services;

import ir.bigchiu.clothing.entities.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
}
