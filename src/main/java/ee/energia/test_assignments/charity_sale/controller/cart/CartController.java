package ee.energia.test_assignments.charity_sale.controller.cart;

import ee.energia.test_assignments.charity_sale.model.Product;
import ee.energia.test_assignments.charity_sale.service.cart.ShoppingCartService;
import ee.energia.test_assignments.charity_sale.service.product.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@Api(value = "Cart", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping(value = "api/shoppingCart")
public class CartController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService<Product> productService;

    @Autowired
    public CartController(ShoppingCartService shoppingCartService, ProductService<Product> productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @ApiOperation(value = "Get shopping cart")
    @GetMapping
    public Map<String, Integer> getShoppingCart() {
        return shoppingCartService.getCart();
    }

    @ApiOperation(value = "Get total price")
    @GetMapping("/total")
    public BigDecimal getCartTotal() {
        return shoppingCartService.getTotal();
    }

    @ApiOperation(value = "Add product to cart (or increment by 1)")
    @PostMapping("/{id}")
    public Map<String, Integer> addProductToCart(@PathVariable("id") int id) {
        productService.findById(id).ifPresent(shoppingCartService::addProduct);
        return shoppingCartService.getCart();
    }

    @ApiOperation(value = "Decrement product quantity in cart by 1")
    @DeleteMapping("/{id}")
    public Map<String, Integer> decrementProduct(@PathVariable("id") int id) {
        productService.findById(id).ifPresent(shoppingCartService::decrementProduct);
        return shoppingCartService.getCart();
    }

    @ApiOperation(value = "Delete a product from cart")
    @DeleteMapping("/{id}/all")
    public Map<String, Integer> deleteFromCart(@PathVariable("id") int id) {
        productService.findById(id).ifPresent(shoppingCartService::deleteProduct);
        return shoppingCartService.getCart();
    }

    @ApiOperation(value = "Empty shopping cart")
    @DeleteMapping
    public Map<String, Integer> clearCart() {
        shoppingCartService.clearCart();
        return shoppingCartService.getCart();
    }

    @ApiOperation(value = "Checkout with cash")
    @PostMapping("/checkout/{cashInserted}")
    public Map <String, Integer> checkout(@Valid @PathVariable("cashInserted") BigDecimal cashInserted) {
        // try checking out
        // on exception throw controller advice will take care of it
        BigDecimal amountToBePaid = shoppingCartService.getTotal();
        shoppingCartService.checkout(cashInserted);
        return shoppingCartService.calculateChange(cashInserted, amountToBePaid);
    }

}