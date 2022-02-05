package ir.inabama.clothing.models;

import ir.inabama.clothing.entities.ClothingItem;
import lombok.Data;

import java.util.List;

@Data
public class ClothingRequest {
	private ClothingItem item;
	private List<String> colors;
}
