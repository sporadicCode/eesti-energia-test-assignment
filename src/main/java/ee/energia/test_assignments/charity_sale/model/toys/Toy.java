package ee.energia.test_assignments.charity_sale.model.toys;

import ee.energia.test_assignments.charity_sale.model.Category;
import ee.energia.test_assignments.charity_sale.model.Product;

import javax.persistence.Entity;

@Entity
public class Toy extends Product {
    @Override
    public Category getCategory() {
        return Category.TOYS;
    }
}
