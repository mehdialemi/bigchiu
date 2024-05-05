package ir.inabama.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public void addAttributeToProduct() throws ProductException, JsonProcessingException {
        Product product = new Product();
        product.setName("pName1");
        product.setDescription("description");
        Product product1 = productService.addProduct(product);

        ProductAttribute attribute = new ProductAttribute();
        attribute.setName("attribute_name_1");
        attribute.setValue("attribute_value_1");
        productService.addAttribute(product1.getId(), attribute);

        ProductAttribute attribute2 = new ProductAttribute();
        attribute2.setName("attribute_name_2");
        attribute2.setValue("attribute_value_2");
        productService.addAttribute(product1.getId(), attribute2);

        Product product2 = productService.get(product1.getId());
        Assert.assertFalse(product2.getAttributes().isEmpty());
        int size = product2.getAttributes().size();
        Assert.assertEquals(2, size);
//        Assert.assertEquals("attribute_name_1", first.getName());
//        Assert.assertEquals("attribute_value_1", first.getValue());

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String json = objectMapper.writeValueAsString(product2);
        System.out.println(json);
    }


}
