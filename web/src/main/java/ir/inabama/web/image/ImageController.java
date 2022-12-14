package ir.inabama.web.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

@Controller
public class ImageController {

    @Autowired
    private ImageService service;

    @PostMapping("/image/upload")
    public String save(@RequestParam("productId") Long productId, @RequestParam("image") MultipartFile file)
            throws IOException {
        Image image = service.save(productId, file.getOriginalFilename(), file.getBytes());
        return "redirect:/admin/product/create?id=" + image.getProduct().getId();
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public void get(@PathVariable("id") String id, HttpServletResponse response)
            throws Exception {
        Image image = service.get(id);
        String types = "image/jpeg, image/jpg, image/png, image/gif";
        response.setContentType(types);
        response.getOutputStream().write(image.getImageBytes());
        response.getOutputStream().close();
    }

}
