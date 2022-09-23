package ir.inabama.shopping.clothes;

import ir.inabama.shopping.config.SessionService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClothingService {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ClothingRepository repository;

    public void add(ClothingItem item, List<String> colors) {
        List <ClothingItem> list = colors.stream().map(color -> {
            item.setColor(color);
            return item;
        }).collect(Collectors.toList());
        addAll(list);
    }

    public void addAll(List<ClothingItem> items) {
        repository.saveAll(items);
    }

    public void add(ClothingItem item) {
        repository.save(item);
    }

    public ClothingItems search(ClothingQuery filterQuery) {
        Criteria criteria = sessionService.getSession().createCriteria(ClothingItem.class);

        for (Criterion filter : filterQuery.filters()) {
            criteria = criteria.add(filter);
        }

		List<ClothingItem> list = criteria.list();
        ClothingItems items = new ClothingItems();
        items.setItems(list);
        return items;
	}
}
