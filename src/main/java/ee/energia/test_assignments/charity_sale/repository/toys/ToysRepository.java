package ee.energia.test_assignments.charity_sale.repository.toys;

import ee.energia.test_assignments.charity_sale.model.toys.Toy;
import ee.energia.test_assignments.charity_sale.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToysRepository extends ProductRepository<Toy> {
}
