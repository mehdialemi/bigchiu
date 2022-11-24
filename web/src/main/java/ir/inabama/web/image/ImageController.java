package ir.inabama.web.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("image")
public class ImageController {

	@Autowired
	private ImageService service;

	@PostMapping("/upload")
	public String save(@RequestParam("image") MultipartFile file, Model model) throws IOException {
		Image image = service.save(file.getOriginalFilename(), file.getBytes());
		model.addAttribute("image", image);
		return "image";
	}

	@GetMapping("{id}")
	@ResponseBody
	void get(@PathVariable("id") String imageId, HttpServletResponse response)
			throws Exception {
		Image image = service.get(imageId);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(image.getImageBytes());
		response.getOutputStream().close();
	}

}
