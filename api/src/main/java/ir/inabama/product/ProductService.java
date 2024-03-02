package ir.inabama.product;

import ir.inabama.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductAttributeRepository attributeRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductAttributeRepository attributeRepository) {
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product get(Long id) {
        return productRepository.getById(id);
    }

    public Product addCategory(Long id, Category category) {
        Product product = productRepository.getById(id);
        product.setCategory(category);
        return productRepository.save(product);
    }

    /**
     * Add a new attribute to the product
     * @param productId
     * @param attribute
     * @return
     * @throws ProductException
     */
    public void addAttribute(long productId, ProductAttribute attribute) throws ProductException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> ProductException.notFound(productId));

        attribute.setProduct(product);
        attributeRepository.save(attribute);
    }
}
