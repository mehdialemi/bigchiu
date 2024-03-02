package ir.inabama.product;

import ir.inabama.category.Category;
import ir.inabama.category.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void addProduct() {
        Product product = new Product();
        product.setName("p1");
        Product result = productService.addProduct(product);

        Product received = productService.get(result.getId());
        Assert.assertEquals(result.getName(), received.getName());
    }

    @Test
    public void addCategoryToProduct() {
        Product product = new Product();
        product.setName("p1");
        Product r1Product = productService.addProduct(product);

        Category category = new Category();
        category.setName("c1");
        Category r1Category = categoryService.add(category);

        Product r2Result = productService.addCategory(r1Product.getId(), r1Category);
        Assert.assertEquals(category.getName(), r2Result.getCategory().getName());
    }

    @Test
    public void addAttributeToProduct() throws ProductException {
        Product product = new Product();
        product.setName("p1");
        Product product1 = productService.addProduct(product);

        ProductAttribute attribute = new ProductAttribute();
        attribute.setValue("v1");
        attribute.setName("n1");
        productService.addAttribute(product1.getId(), attribute);

        Product product2 = productService.get(product1.getId());
        Assert.assertFalse(product2.getAttributes().isEmpty());
        ProductAttribute first = product2.getAttributes().stream().findFirst().get();
        Assert.assertEquals("n1", first.getName());
        Assert.assertEquals("v1", first.getValue());
    }


}
