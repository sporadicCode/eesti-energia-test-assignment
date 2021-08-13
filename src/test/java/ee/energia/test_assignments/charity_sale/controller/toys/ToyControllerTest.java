package ee.energia.test_assignments.charity_sale.controller.toys;

import ee.energia.test_assignments.charity_sale.CharitySaleApplication;
import ee.energia.test_assignments.charity_sale.model.food.Food;
import ee.energia.test_assignments.charity_sale.model.toys.Toy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = CharitySaleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/toys";
    }

    @Test
    void saveToy() {
        Toy toy = new Toy();
        toy.setStock(3);
        toy.setName("Test toy");
        toy.setPrice(new BigDecimal("3.85"));
        toy.setImageUrl("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.willcookforsmiles.com%2Fwp-content%2Fuploads%2F2017%2F05%2FTaco-Burger-2.jpg");
        assertNotNull(restTemplate.postForObject(getBaseUrl(), toy, Toy.class));
    }

    @Test
    void findToyById() {
        assertNotNull(restTemplate.getForObject(getBaseUrl() + "/9", Food.class));
    }

    @Test
    void findAllToys() {
        assertNotNull(restTemplate.getForObject(getBaseUrl(), List.class));
    }

    @Test
    void deleteToyById() {
        assertEquals(1, restTemplate.getForObject(getBaseUrl(), List.class).size());
        restTemplate.delete(getBaseUrl() + "/9");
        assertEquals(0, restTemplate.getForObject(getBaseUrl(), List.class).size());
    }

    @Test
    void deleteAllToys() {
        restTemplate.delete(getBaseUrl());
        assertEquals(0, restTemplate.getForObject(getBaseUrl(), List.class).size());
    }
}