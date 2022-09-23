package ir.inabama.web.display;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DisplayService {

	public DisplayBox getFeaturedBox() {
		return createDisplayBox("برگزیده", "product", Arrays.asList("1","2","3","4"));
	}

	public DisplayBox getRecommendedBox() {
		DisplayBox displayBox = createDisplayBox("پیشنهادات", "", Arrays.asList("1", "2"));
		for (int i = 0; i < displayBox.getItems().size(); i++) {
			DisplayBoxItem item = displayBox.getItems().get(i);
			DisplayBox box = createDisplayBox(displayBox.getTitle(), "recommend", Arrays.asList("1", "2", "3"));
			item.setBox(box);
		}
		return displayBox;
	}

	public DisplayBox getCategoryBox() {
		List <String> names = Arrays.asList("tshirt", "blazers", "sunglass", "", "poloshirt");
		DisplayBox displayBox = createDisplayBox("دسته بندی", "gallery", names);

		for (int i = 0; i < displayBox.getItems().size(); i++) {
			DisplayBoxItem item = displayBox.getItems().get(i);
			DisplayBox box = createDisplayBox(displayBox.getTitle(), "gallery", Arrays.asList("1", "2", "3", "4"));
			item.setBox(box);
			item.setCss(displayBox.getTitle());
		}
		return displayBox;
	}

	private DisplayBox createDisplayBox(String title, String img, List<String> names) {
		final DisplayBox displayBox = new DisplayBox();
		displayBox.setTitle(title);

		for (int i = 0; i < names.size(); i++) {
			DisplayBoxItem item = new DisplayBoxItem();
			String name = names.get(i);
			item.setName(name);
			item.setSubject(title);
			item.setCss(name);
			item.setOverlayText(name);
			item.setOverlayTitle(title);
			item.setImageUrl("images/home/"+ img + (i + 1) + ".jpg");
			if (i == 0) {
				item.setActive(true);
			} else {
				item.setActive(false);
			}
			displayBox.getItems().add(item);
		}

		return displayBox;
	}

}
