package ir.inabama.web.clothing;

import lombok.Data;

import java.util.List;

@Data
public class ClothingRequest {
	private ClothingItem item;
	private List<String> colors;
}
