package ee.energia.test_assignments.charity_sale.service.cart;

import ee.energia.test_assignments.charity_sale.exception.NotEnoughStockException;
import ee.energia.test_assignments.charity_sale.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public interface ShoppingCartService {
    void addProduct(Product product);
    void decrementProduct(Product product);
    void deleteProduct(Product product);
    Map<String, Integer> getCart();
    BigDecimal getTotal();
    void checkout(BigDecimal cashInserted) throws NotEnoughStockException;
    void clearCart();
    Map<String, Integer> calculateChange(BigDecimal cashInserted, BigDecimal amountToBePaid);
}
