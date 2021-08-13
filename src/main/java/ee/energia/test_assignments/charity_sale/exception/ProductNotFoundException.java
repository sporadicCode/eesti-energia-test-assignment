package ee.energia.test_assignments.charity_sale.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(int id) {
        super(String.format("Product with ID of %d couldn't be found!", id));
    }
}
