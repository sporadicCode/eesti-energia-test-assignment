package ee.energia.test_assignments.charity_sale.repository;

import ee.energia.test_assignments.charity_sale.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository<T extends Product> extends JpaRepository<T, Integer> {
}
