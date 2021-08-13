package ee.energia.test_assignments.charity_sale.model.clothes;

import ee.energia.test_assignments.charity_sale.model.Category;
import ee.energia.test_assignments.charity_sale.model.Product;

import javax.persistence.Entity;

@Entity
public class Clothes extends Product {
    @Override
    public Category getCategory() {
        return Category.CLOTHES;
    }
}