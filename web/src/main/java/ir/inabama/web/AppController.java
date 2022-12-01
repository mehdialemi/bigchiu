package ir.inabama.web;

import ir.inabama.web.display.MyMeta;
import ir.inabama.web.display.DisplayBox;
import ir.inabama.web.display.DisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {

	@Autowired
	private DisplayService displayService;

	@GetMapping("/")
	public String index(HttpServletRequest request, Model model) {

		MyMeta myMeta = new MyMeta();
		try {
			if (request.isUserInRole("ADMIN")) {
				myMeta.setLogin(true);
				return "admin/index";
			} else {
				if (request.isUserInRole("USER")) {
					myMeta.setLogin(true);
				}

				return "index";
			}
		} finally {
			model.addAttribute("mymeta", myMeta);
			model.addAttribute("featuredBox", displayService.getFeaturedBox());
			model.addAttribute("recommendedBox", displayService.getRecommendedBox());
			model.addAttribute("categoryBox", displayService.getCategoryBox());
		}
	}

	@GetMapping("/admin")
	public String admin() { return "/admin/index";}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login?logout")
	public String logout() {
		return "/";
	}
}
