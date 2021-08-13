package ee.energia.test_assignments.charity_sale.controller.product;


import ee.energia.test_assignments.charity_sale.model.Product;
import ee.energia.test_assignments.charity_sale.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Api(value = "Product", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository<Product> productRepository;

    @Autowired
    public ProductController(ProductRepository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    @ApiOperation(value = "Get all products")
    @GetMapping
    public List<Product> findAllProducts() {
        List<Product> productList = productRepository.findAll();
        productList.sort(Comparator.comparing(Product::getId));
        return productList;
    }

    @ApiOperation(value = "Delete all products")
    @DeleteMapping
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
