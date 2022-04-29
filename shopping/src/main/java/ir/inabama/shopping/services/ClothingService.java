package ir.inabama.shopping.services;

import ir.inabama.shopping.config.SessionService;
import ir.inabama.shopping.clothes.ClothingItem;
import ir.inabama.shopping.clothes.ClothingQuery;
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

    public List<ClothingItem> search(ClothingQuery filterQuery) {
        Criteria criteria = sessionService.getSession().createCriteria(ClothingItem.class);

        for (Criterion filter : filterQuery.filters()) {
            criteria = criteria.add(filter);
        }

        return criteria.list();
    }
}
