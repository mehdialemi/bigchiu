package ir.inabama.clothing;

import ir.inabama.clothing.entities.ClothingItem;
import ir.inabama.clothing.models.ClothingItems;
import ir.inabama.clothing.models.ClothingQuery;
import ir.inabama.clothing.models.ClothingRequest;
import ir.inabama.clothing.services.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop/clothing")
public class ClothingController {

	@Autowired
	private ClothingService service;

	@PostMapping("/add")
	public void add(@RequestBody ClothingRequest request) {
		service.add(request.getItem(), request.getColors());
	}

	@PostMapping("/search")
	public ClothingItems search(@RequestBody ClothingQuery query) {
		List <ClothingItem> items = service.findAll(query);
		ClothingItems clothingItems = new ClothingItems();
		clothingItems.setItems(items);
		return clothingItems;
	}
}
