package ee.energia.test_assignments.charity_sale.controller.cart;

import ee.energia.test_assignments.charity_sale.CharitySaleApplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = CharitySaleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/shoppingCart";
    }

    @Test
    void getShoppingCart() {
        assertEquals("{}", restTemplate.getForObject(getBaseUrl(), String.class));
    }

    @Test
    void getCartTotal() {
        assertEquals(0, restTemplate.getForObject(getBaseUrl() + "/total", Integer.class));
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/1", "POST", Map.class));
        assertNotEquals(new BigDecimal("1.00"), restTemplate.getForObject(getBaseUrl() + "/total", BigDecimal.class));
    }

    @Test
    void addProductToCart() {
        assertEquals("{}", restTemplate.getForObject(getBaseUrl(), String.class));
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/1", null, Map.class));
    }

    @Test
    void decrementProduct() {
        assertEquals("{}", restTemplate.getForObject(getBaseUrl(), String.class));
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/1", null, Map.class));
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/1", null, Map.class));
        restTemplate.delete(getBaseUrl() + "/1");
        assertNotNull(restTemplate.getForObject(getBaseUrl(), Map.class));
    }

    @Test
    void deleteFromCart() {
        assertEquals("{}", restTemplate.getForObject(getBaseUrl(), String.class));
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/1", null, Map.class));
        restTemplate.delete(getBaseUrl() + "/1/all");
        assertEquals("{}", restTemplate.getForObject(getBaseUrl(), String.class));
    }

    @Test
    void clearCart() {
        assertEquals("{}", restTemplate.getForObject(getBaseUrl(), String.class));
        assertEquals(0, restTemplate.getForObject(getBaseUrl() + "/total", Integer.class));
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/1", null, Map.class));
        restTemplate.delete(getBaseUrl());
        assertEquals("{}", restTemplate.getForObject(getBaseUrl(), String.class));
        assertEquals(0, restTemplate.getForObject(getBaseUrl() + "/total", Integer.class));
    }

    @Test
    void checkout() {
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/1", null, Map.class));
        assertNotNull(restTemplate.postForEntity(getBaseUrl() + "/checkout/1", null, Map.class));
    }
}