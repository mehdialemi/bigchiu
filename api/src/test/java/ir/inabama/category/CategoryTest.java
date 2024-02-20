package ir.inabama.category;

import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void addCategory() {
        Category category = new Category();
        category.setName("c1");
        Category result = categoryService.add(category);

        Category received = categoryService.get(result.getId());
        Assert.assertEquals(result.getName(), received.getName());
    }

    @Test
    public void testChildParent() {
        Category p1 = new Category();
        p1.setName("p1");
        Category cat1 = categoryService.add(p1);

        Category p2 = new Category();
        p2.setName("p2");
        p2.setParent(cat1);

        Category add = categoryService.add(p2);

        Category p1Result = categoryService.getByName(p1.getName());
        Assert.assertEquals(p2.getName(), p1Result.getChildren().stream().findFirst().get().getName());
    }

}
