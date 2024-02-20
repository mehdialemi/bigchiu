package ir.inabama.product;

import ir.inabama.category.Category;
import ir.inabama.category.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
