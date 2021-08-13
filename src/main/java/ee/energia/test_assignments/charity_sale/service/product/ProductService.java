package ee.energia.test_assignments.charity_sale.service.product;

import ee.energia.test_assignments.charity_sale.model.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService<T extends Product> {
    Optional<T> findById(int id);
}
