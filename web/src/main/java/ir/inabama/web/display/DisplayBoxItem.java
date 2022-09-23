package ir.inabama.web.display;

import lombok.Data;

@Data
public class DisplayBoxItem {
	public String name;
	public String subject;
	public String text;
	public String imageUrl;
	public String overlayTitle;
	public String overlayText;
	public DisplayBox box;
	public String css;
	public boolean active;
}
