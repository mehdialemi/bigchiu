package ir.inabama.shopping.clothes;

import ir.inabama.shopping.clothes.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
}
