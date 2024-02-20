package ir.inabama.product;

import ir.inabama.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product get(Long id) {
        return repository.getById(id);
    }

    public Product addCategory(Long id, Category category) {
        Product product = repository.getById(id);
        product.setCategory(category);
        return repository.save(product);
    }
}
