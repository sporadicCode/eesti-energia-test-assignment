package ee.energia.test_assignments.charity_sale.repository.clothes;

import ee.energia.test_assignments.charity_sale.model.clothes.Clothes;
import ee.energia.test_assignments.charity_sale.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends ProductRepository<Clothes> {
}
