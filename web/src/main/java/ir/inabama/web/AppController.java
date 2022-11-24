package ir.inabama.web;

import ir.inabama.web.display.ClientData;
import ir.inabama.web.display.DisplayBox;
import ir.inabama.web.display.DisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@Autowired
	private DisplayService displayService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("client", new ClientData());

		DisplayBox featuredBox = displayService.getFeaturedBox();
		model.addAttribute("featuredBox", featuredBox);

		DisplayBox recommendedBox = displayService.getRecommendedBox();
		model.addAttribute("recommendedBox", recommendedBox);

		DisplayBox categoryBox = displayService.getCategoryBox();
		model.addAttribute("categoryBox", categoryBox);

		return "index";
	}

	@GetMapping("/admin")
	public String admin() { return "/admin/admin";}
}
