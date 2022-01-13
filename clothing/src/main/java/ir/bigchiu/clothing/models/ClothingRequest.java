package ir.bigchiu.clothing.models;

import ir.bigchiu.clothing.entities.ClothingItem;
import lombok.Data;

import java.util.List;

@Data
public class ClothingRequest {
	private ClothingItem item;
	private List<String> colors;
}
