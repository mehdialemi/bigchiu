package ir.inabama.web.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/details")
	public String productDetails(@RequestParam String id, Model model) {
		return "product/product-details.html";
	}

}
