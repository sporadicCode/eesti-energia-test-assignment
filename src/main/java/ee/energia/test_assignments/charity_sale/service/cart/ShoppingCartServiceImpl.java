package ee.energia.test_assignments.charity_sale.service.cart;

import ee.energia.test_assignments.charity_sale.exception.NotEnoughCashException;
import ee.energia.test_assignments.charity_sale.exception.NotEnoughStockException;
import ee.energia.test_assignments.charity_sale.model.Product;
import ee.energia.test_assignments.charity_sale.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ProductRepository<Product> productRepository;
    private final Map<Product, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(ProductRepository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * If product is in cart, increment quantity by 1
     * If product is not in cart, add product to cart with quantity of 1
     *
     * @param product Product object to add to cart
     * @throws NotEnoughStockException if product stock is exceeded
     */
    @Override
    public void addProduct(Product product) throws NotEnoughStockException {
        if (products.containsKey(product)) {
            if (product.getStock() > products.get(product)) {
                products.replace(product, products.get(product) + 1);
            } else {
                throw new NotEnoughStockException();
            }

        } else {
            if (product.getStock() > 0) {
                products.put(product, 1);
            }
        }
    }

    /**
     * If product is in cart, decrement quantity by 1
     * If product quantity is 1, delete product from cart
     *
     * @param product Product object to remove from cart
     */
    @Override
    public void decrementProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    /**
     * Delete a product from cart aka remove it completely
     * Quantity doesn't matter
     *
     * @param product Product object to delete from cart
     */
    @Override
    public void deleteProduct(Product product) {
        products.remove(product);
    }

    /**
     * Get cart for current session
     *
     * @return cart
     */
    @Override
    public Map<String, Integer> getCart() {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            final Product entryKey = entry.getKey();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", entryKey.getId());
                jsonObject.put("name", entryKey.getName());
                jsonObject.put("price", entryKey.getPrice());
                jsonObject.put("stock", entryKey.getStock());
                jsonObject.put("image_url", entryKey.getImageUrl());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(jsonObject.toString(), entry.getValue());
        }
        return map;
    }

    /**
     * Empty the shopping cart.
     */
    @Override
    public void clearCart() {
        products.clear();
    }

    /**
     * Checkout will fail if not enough product in stock.
     *
     * @throws NotEnoughStockException thrown if not enough stock for successful checkout
     */
    @Override
    public void checkout(BigDecimal cashInserted) throws NotEnoughStockException, NotEnoughCashException {
        if (getTotal().compareTo(cashInserted) > 0) {
            throw new NotEnoughCashException();
        }
        Optional<Product> product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // refresh stock of products before attempting checkout
            product = productRepository.findById(entry.getKey().getId());
            product.ifPresent((item) -> {
                if (entry.getValue() > item.getStock()) {
                    throw new NotEnoughStockException();
                }
                entry.getKey().setStock(item.getStock() - entry.getValue());
            });
        }
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // update stock
            product = productRepository.findById(entry.getKey().getId());
            product.ifPresent((p)-> p.setStock(p.getStock() - entry.getValue()));
        }
        products.clear();
    }

    /**
     * Checkout will fail if not enough product in stock.
     *
     * @param amountToBePaid how much the customer should pay
     * @param currencyUnits available currency banknotes / coins
     * @return a map of change to be returned: currency units as keys and their count as values
     */
    private Map<String, Integer> splitIntoCurrencyUnits(BigDecimal amountToBePaid, BigDecimal[] currencyUnits) {
        Map<String, Integer> currencyMap = new LinkedHashMap<>();
        for (BigDecimal unit : currencyUnits) {
            if (amountToBePaid.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
            BigDecimal count = amountToBePaid.divideToIntegralValue(unit);
            if (count.compareTo(BigDecimal.ONE) >= 0) {
                currencyMap.put(unit.toString(), count.intValue());
                amountToBePaid = amountToBePaid.subtract(count.multiply(unit));
            }
        }
        return currencyMap;
    }

    /**
     * Checkout will fail if not enough product in stock.
     *
     * @param cashInserted how much cash did the customer insert
     * @param amountToBePaid how much the customer's cart is worth
     * @return a map of change to be returned: currency units as keys and their count as values
     */
    @Override
    public Map<String, Integer> calculateChange(BigDecimal cashInserted, BigDecimal amountToBePaid) {
        Map<String, Integer> changeReturned = new HashMap<>();
        if (amountToBePaid.compareTo(cashInserted) == 0) {
            changeReturned.put("change", 0);
            return changeReturned;
        }
        BigDecimal change = cashInserted.subtract(amountToBePaid);
        BigDecimal centsToBeReturned = change.remainder(BigDecimal.ONE);
        BigDecimal eurosToBeReturned = change.subtract(centsToBeReturned);

        BigDecimal[] centsInCirculation = {
                new BigDecimal("0.50"), new BigDecimal("0.20"), new BigDecimal("0.10"),
                new BigDecimal("0.05"), new BigDecimal("0.02"), new BigDecimal("0.01")
        };
        BigDecimal[] eurosInCirculation = {
                new BigDecimal("500.00"), new BigDecimal("200.00"), new BigDecimal("100.00"),
                new BigDecimal("50.00"), new BigDecimal("20.00"), new BigDecimal("10.00"),
                new BigDecimal("5.00"), new BigDecimal("2.00"), new BigDecimal("1.00")
        };

        Map<String, Integer> map1 = splitIntoCurrencyUnits(eurosToBeReturned, eurosInCirculation);
        Map<String, Integer> map2 = splitIntoCurrencyUnits(centsToBeReturned, centsInCirculation);

        return Stream
                .concat(
                        map1.entrySet().stream(),
                        map2.entrySet().stream()
                )
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // merge function
                        LinkedHashMap::new
                ));
    }

    /**
     * Get total price of the whole cart
     *
     * @return cart total price
     */
    @Override
    public BigDecimal getTotal() {
        return products
                .entrySet()
                .stream()
                .map(entry -> entry
                        .getKey()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}

