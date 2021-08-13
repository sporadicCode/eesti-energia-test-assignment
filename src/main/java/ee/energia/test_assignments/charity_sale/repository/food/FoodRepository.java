package ee.energia.test_assignments.charity_sale.repository.food;

import ee.energia.test_assignments.charity_sale.model.food.Food;
import ee.energia.test_assignments.charity_sale.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends ProductRepository<Food> {
}
