package ir.inabama.shopping.controllers;

import ir.inabama.shopping.clothes.ClothingItem;
import ir.inabama.shopping.clothes.ClothingItems;
import ir.inabama.shopping.clothes.ClothingQuery;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unbescape.html.HtmlEscape;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/")
public class MainController {

	@RequestMapping("/")
	public String root() {
		return "index.html";
	}


	/** User zone index. */
	@RequestMapping("/user/user.html")
	public String userIndex() {
		return "user/index";
	}

	/** Administration zone index. */
	@RequestMapping("/admin/admin.html")
	public String adminIndex() {
		return "admin/index";
	}

	/** Shared zone index. */
	@RequestMapping("/shared/index.html")
	public String sharedIndex() {
		return "shared/index";
	}

	/** Login form. */
	@RequestMapping("/login")
	public String login() {
		return "/auth/login";
	}

	/** Login form with error. */
	@RequestMapping("/login-error.html")
	public String loginError(Model model) {
		model.addAttribute("loginError", Boolean.TRUE);
		return "login";
	}

	/** Simulation of an exception. */
	@RequestMapping("/simulateError.html")
	public void simulateError() {
		throw new RuntimeException("This is a simulated error message");
	}

	/** Error page. */
	@RequestMapping("/error.html")
	public String error(HttpServletRequest request, Model model) {
		model.addAttribute("errorCode", "Error " + request.getAttribute("javax.servlet.error.status_code"));
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("<ul>");
		while (throwable != null) {
			errorMessage.append("<li>").append(HtmlEscape.escapeHtml5(throwable.getMessage())).append("</li>");
			throwable = throwable.getCause();
		}
		errorMessage.append("</ul>");
		model.addAttribute("errorMessage", errorMessage.toString());
		return "error";
	}

	/** Error page. */
	@RequestMapping("/403.html")
	public String forbidden() {
		return "403";
	}


}
