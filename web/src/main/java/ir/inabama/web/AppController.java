package ir.inabama.web;

import ir.inabama.web.display.DisplayService;
import ir.inabama.web.display.MyMeta;
import ir.inabama.web.product.Product;
import ir.inabama.web.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppController {

    @Autowired
    private DisplayService displayService;

    @Autowired
    private ProductService productService;

    private MyMeta init(HttpServletRequest request) {
        MyMeta myMeta = new MyMeta();
        if (request.isUserInRole("ADMIN")) {
            myMeta.setLogin(true);
            myMeta.setAdmin(true);
        } else {
            if (request.isUserInRole("USER")) {
                myMeta.setLogin(true);
            }
        }
        return myMeta;
    }

    private void updateMyMeta(Model model, MyMeta myMeta) {
        model.addAttribute("mymeta", myMeta);
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        MyMeta myMeta = init(request);
        try {
            if (myMeta.isAdmin()) {
                return "admin/index";
            } else {
                return "index";
            }
        } finally {
            updateMyMeta(model, myMeta);
            model.addAttribute("featuredBox", displayService.getFeaturedBox());
            model.addAttribute("recommendedBox", displayService.getRecommendedBox());
            model.addAttribute("categoryBox", displayService.getCategoryBox());
        }
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request, Model model) {
        MyMeta myMeta = init(request);
        updateMyMeta(model, myMeta);
        return "/admin/index";
    }

    @GetMapping("/admin/product/list")
    public String listProducts(HttpServletRequest request, Model model) {
        MyMeta myMeta = init(request);
        myMeta.setListProducts(true);
        updateMyMeta(model, myMeta);
        return "/admin/index";
    }

    @GetMapping("/admin/product/create")
    public String createProduct(@RequestParam(value = "productId", required = false) Long productId, HttpServletRequest request, Model model) {
        MyMeta myMeta = init(request);
        myMeta.setCreateProduct(true);
        updateMyMeta(model, myMeta);
        Product product = (productId == null) ? productService.create() : productService.get(productId);
        model.addAttribute("product", product);
        return "/admin/index";
    }


    @GetMapping("/admin/sellers/list")
    public String sellers(HttpServletRequest request, Model model) {
        MyMeta myMeta = init(request);
        myMeta.setSellers(true);
        updateMyMeta(model, myMeta);
        return "/admin/index";
    }

    @GetMapping("/admin/finance")
    public String finance(HttpServletRequest request, Model model) {
        MyMeta myMeta = init(request);
        myMeta.setFinance(true);
        updateMyMeta(model, myMeta);
        return "/admin/index";
    }

    @GetMapping("/admin/reports")
    public String reports(HttpServletRequest request, Model model) {
        MyMeta myMeta = init(request);
        myMeta.setReports(true);
        updateMyMeta(model, myMeta);
        return "/admin/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login?logout")
    public String logout() {
        return "/";
    }
}
