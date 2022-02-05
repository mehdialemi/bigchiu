package ir.inabama.clothing.models;

import ir.inabama.clothing.entities.ClothingItem;
import lombok.Data;

import java.util.List;

@Data
public class ClothingItems {
	private List<ClothingItem> items;
}
