package ir.inabama.shopping.services;

import ir.inabama.shopping.clothes.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
}
