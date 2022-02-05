package ir.inabama.web.clothing;

import ir.bigchiu.shop.clothing.ClothingItem;
import ir.bigchiu.shop.clothing.ClothingItems;
import ir.bigchiu.shop.clothing.ClothingQuery;
import ir.bigchiu.shop.common.SortType;
import ir.inabama.web.connect.ShopConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class CollectionService {

	@Value("${first-page.clothing.new.count}")
	private int newCount;

	@Value("${first-page.clothing.popullar.count}")
	private int popularCount;

	@Autowired
	private ShopConnector connector;

	private ShopClothingService service;

	@PostConstruct
	public void init() {
		service = connector.getRetrofit().create(ShopClothingService.class);
	}

	public List <ClothingItem> getNewItems() {
		ClothingQuery query = createQuery(SortType.LATEST, newCount);
		ClothingItems items = connector.execute(service.search(query));
		return items.getItems();
	}

	public List<ClothingItem> getPopularItems() {
		ClothingQuery query = createQuery(SortType.BUY_COUNT, popularCount);
		ClothingItems items = connector.execute(service.search(query));
		return items.getItems();
	}

	private ClothingQuery createQuery(SortType sortType, int limit) {
		ClothingQuery query = new ClothingQuery();
		query.setSortType(sortType);
		query.setLimit(limit);
		return query;
	}


}