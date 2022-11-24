package ir.inabama.web.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ImageService {

	@Autowired
	private ImageRepository repository;

	public Image save(String fileName, byte[] bytes) {
		Image image = new Image();
		image.setFileName(fileName);
		image.setImageBytes(bytes);
		image.setImageId(UUID.randomUUID().toString());
		return repository.save(image);
	}

	public Image get(String imageId) throws Exception {
		return repository.getByImageId(imageId).orElseThrow(Exception::new);
	}

}
