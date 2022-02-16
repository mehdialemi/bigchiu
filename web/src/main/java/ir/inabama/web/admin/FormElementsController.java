package ir.inabama.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class FormElementsController {

    @GetMapping(value = "form-elements")
    public ModelMap mmFormElements() {
        return new ModelMap();
    }

}
