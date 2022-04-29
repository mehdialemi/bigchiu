//package ir.inabama.web.admin;
//
//import ir.inabama.user.user.User;
//import ir.inabama.user.user.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/admin/")
//public class PersonController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/users")
//    public List<User> findAll() {
//        return userService.findAll();
//    }
//
//}
