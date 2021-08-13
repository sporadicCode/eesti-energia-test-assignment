package ee.energia.test_assignments.charity_sale.controller.product;

import ee.energia.test_assignments.charity_sale.CharitySaleApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = CharitySaleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/products";
    }

    @Test
    void findAllProducts() {
        assertEquals(9, this.restTemplate.getForObject(getBaseUrl(), List.class).size());
    }

    @Test
    void deleteAllProducts() {
        assertEquals(9, this.restTemplate.getForObject(getBaseUrl(), List.class).size());
        restTemplate.delete(getBaseUrl());
        assertEquals(0, this.restTemplate.getForObject(getBaseUrl(), List.class).size());
    }
}