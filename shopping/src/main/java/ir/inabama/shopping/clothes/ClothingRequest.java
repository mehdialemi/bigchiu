package ir.inabama.shopping.clothes;

import lombok.Data;

import java.util.List;

@Data
public class ClothingRequest {
	private ClothingItem item;
	private List<String> colors;
}
