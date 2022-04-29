package ir.inabama.shopping.controllers;

import ir.inabama.shopping.clothes.ClothingItem;
import ir.inabama.shopping.clothes.ClothingItems;
import ir.inabama.shopping.clothes.ClothingQuery;
import ir.inabama.shopping.services.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	private ClothingService clothingService;

	@PostMapping("/search")
	public ClothingItems search(@RequestBody ClothingQuery query) {
		List<ClothingItem> items = clothingService.search(query);
		ClothingItems clothingItems = new ClothingItems();
		clothingItems.setItems(items);
		return clothingItems;
	}
}
