package ee.energia.test_assignments.charity_sale.controller.food;

import ee.energia.test_assignments.charity_sale.CharitySaleApplication;
import ee.energia.test_assignments.charity_sale.model.food.Food;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = CharitySaleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FoodControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/food";
    }

    @Test
    void saveFood() {
        Food tacoBurger = new Food();
        tacoBurger.setStock(300);
        tacoBurger.setName("Taco burger");
        tacoBurger.setPrice(new BigDecimal("2.45"));
        tacoBurger.setImageUrl("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.willcookforsmiles.com%2Fwp-content%2Fuploads%2F2017%2F05%2FTaco-Burger-2.jpg");
        assertNotNull(restTemplate.postForObject(getBaseUrl(), tacoBurger, Food.class));
    }

    @Test
    void findFoodById() {
        assertNotNull(restTemplate.getForObject(getBaseUrl() + "/1", Food.class));
    }

    @Test
    void findAllFood() {
        assertEquals(5, restTemplate.getForObject(getBaseUrl(), List.class).size());
    }

    @Test
    void deleteFoodById() {
        assertEquals(5, restTemplate.getForObject(getBaseUrl(), List.class).size());
        restTemplate.delete(getBaseUrl() + "/1");
        assertEquals(4, restTemplate.getForObject(getBaseUrl(), List.class).size());
    }
}