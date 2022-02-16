package ir.inabama.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pages")
public class TableElements {

    @GetMapping("table-elements")
    public ModelMap mmTableElements() {
        return new ModelMap();
    }

}
