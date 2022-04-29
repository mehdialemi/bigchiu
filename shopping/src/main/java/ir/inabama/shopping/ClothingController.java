package ir.inabama.shopping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shop/clothing")
public class ClothingController {


	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/admin")
	public String admin() { return "/admin/admin";}
}
