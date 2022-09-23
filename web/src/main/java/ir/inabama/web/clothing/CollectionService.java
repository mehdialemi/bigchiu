package ir.inabama.web.clothing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CollectionService {

	@Value("${first-page.clothing.new.count}")
	private int newCount;

	@Value("${first-page.clothing.popullar.count}")
	private int popularCount;

	@Autowired
	private ClothingService service;

	public List <ClothingItem> getNewItems() {
		ClothingQuery query = createQuery(SortType.LATEST, newCount);
		ClothingItems items = service.search(query);
		return items.getItems();
	}

	public List<ClothingItem> getPopularItems() {
		ClothingQuery query = createQuery(SortType.BUY_COUNT, popularCount);
		ClothingItems items = service.search(query);
		return items.getItems();
	}

	private ClothingQuery createQuery(SortType sortType, int limit) {
		ClothingQuery query = new ClothingQuery();
		query.setSortType(sortType);
		query.setLimit(limit);
		return query;
	}
}