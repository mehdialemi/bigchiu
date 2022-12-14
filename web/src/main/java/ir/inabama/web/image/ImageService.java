package ir.inabama.web.image;

import ir.inabama.web.product.Product;
import ir.inabama.web.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ImageService {

	@Autowired
	private ImageRepository repository;

	@Autowired
	private ProductService productService;

	public Image save(Long productId, String fileName, byte[] bytes) {
		Product product = (productId == null) ? productService.create() : productService.get(productId);
		Image image = new Image();
		image.setFileName(fileName);
		image.setImageBytes(bytes);
		image.setImageId(UUID.randomUUID().toString());
		image.setProduct(product);
		return repository.save(image);
	}

	public Image get(String imageId) throws Exception {
		return repository.getByImageId(imageId).orElseThrow(Exception::new);
	}

}
