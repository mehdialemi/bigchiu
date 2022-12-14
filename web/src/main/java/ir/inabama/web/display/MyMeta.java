package ir.inabama.web.display;

import lombok.Data;

@Data
public class MyMeta {

	private boolean isLogin;
	private boolean error;
	private boolean admin;
	private boolean createProduct;
	private boolean listProducts;
	private boolean sellers;
	private boolean reports;
	private boolean finance;
}
