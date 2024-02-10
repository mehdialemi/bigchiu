package ir.inabama.menu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @GetMapping
    private List<Menu> getMenu() {
        List<Menu> menus = new ArrayList<>();
        Menu menu1 = new Menu();
        Menu.SubMenu subMenu = new Menu.SubMenu();
        subMenu.setTitle("موبایل");

        menu1.setList(List.of(subMenu));
        menu1.setTitle("گوشی موبایل");
        menus.add(menu1);

        Menu menu2 = new Menu();
        menu2.setTitle("صوتی و تصویری");
        menus.add(menu2);
        return menus;
    }
}
