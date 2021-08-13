package ee.energia.test_assignments.charity_sale.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException () {
        super("Not enough products in stock");
    }
}
