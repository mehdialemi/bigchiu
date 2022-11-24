package ir.inabama.web.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@GetMapping("/user/basket")
	public String basket() {
		return "basket";
	}
}
