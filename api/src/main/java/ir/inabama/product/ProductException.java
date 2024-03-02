package ir.inabama.product;

import ir.inabama.common.MyException;

public class ProductException extends MyException {

    private long productId;

    private ProductException(long id, String message) {
        super("product id: " + id + ", " + message);

    }

    public static ProductException notFound(long productId) {
        return new ProductException(productId, "not found");
    }

}
