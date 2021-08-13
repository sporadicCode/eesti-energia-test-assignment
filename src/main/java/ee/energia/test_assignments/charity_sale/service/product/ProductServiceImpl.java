package ee.energia.test_assignments.charity_sale.service.product;

import ee.energia.test_assignments.charity_sale.model.Product;
import ee.energia.test_assignments.charity_sale.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService<Product> {

    private final ProductRepository<Product> productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }
}
