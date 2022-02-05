package ir.inabama.web.clothing;

import ir.bigchiu.shop.clothing.ClothingQuery;
import ir.bigchiu.shop.clothing.ClothingItems;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ShopClothingService {

	@POST("/api/v1/shop/clothing/search")
	Call<ClothingItems> search(@Body ClothingQuery query);
}
