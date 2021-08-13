package ee.energia.test_assignments.charity_sale.exception;

public class NotEnoughCashException extends RuntimeException {
    public NotEnoughCashException () {
        super("Not enough cash to check out the cart.");
    }
}
