package ir.inabama.web.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Product create(Product product) {
		return repository.save(product);
	}

}
