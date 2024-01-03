package ir.inabama.web;

import ir.inabama.web.product.Product;
import ir.inabama.web.product.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Autowired
    private ProductService productService;

    @Test
    public void addProduct() {
        Product product = new Product();
        product.setName("p1");
        Product saved = productService.create(product);
        Assert.assertNotNull(saved);

        Product result = productService.get(saved.getId());
        Assert.assertEquals("p1", result.getName());
    }
}
