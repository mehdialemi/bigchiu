package ir.inabama.web.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Product create(Product product) {
		return repository.save(product);
	}

	public Product create() {
		Product product = new Product();
		return repository.save(product);
	}

	public Product get(Long id) {
		return repository.findById(id).orElse(null);
	}
}
