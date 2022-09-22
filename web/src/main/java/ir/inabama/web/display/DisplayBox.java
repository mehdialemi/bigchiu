package ir.inabama.web.display;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DisplayBox {
	public String title;
	public List<DisplayBoxItem> items = new ArrayList <>();
}
