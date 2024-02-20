package ir.inabama.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;


    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category get(Long id) {
        return repository.getById(id);
    }

    public Category getByName(String name) {
        return repository.findCategoryByName(name).orElse(null);
    }

    public Category add(Category category) {
        return repository.save(category);
    }
}
