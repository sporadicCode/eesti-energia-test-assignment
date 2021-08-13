package ee.energia.test_assignments.charity_sale.controller.clothes;

import ee.energia.test_assignments.charity_sale.CharitySaleApplication;
import ee.energia.test_assignments.charity_sale.model.clothes.Clothes;
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
class ClothesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/clothes";
    }

    @Test
    void saveClothes() {
        Clothes clothes = new Clothes();
        clothes.setStock(20);
        clothes.setName("clothesTEST");
        clothes.setPrice(new BigDecimal("20.45"));
        clothes.setImageUrl("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.willcookforsmiles.com%2Fwp-content%2Fuploads%2F2017%2F05%2FTaco-Burger-2.jpg");
        assertNotNull(restTemplate.postForObject(getBaseUrl() + "/1", clothes, Clothes.class));
    }

    @Test
    void getClothesById() {
        assertNotNull(restTemplate.getForObject(getBaseUrl() + "/7", Clothes.class));
    }

    @Test
    void getAllClothes() {
        assertEquals(3, restTemplate.getForObject(getBaseUrl(), List.class).size());
    }

    @Test
    void deleteClothesById() {
        assertEquals(3, restTemplate.getForObject(getBaseUrl(), List.class).size());
        restTemplate.delete(getBaseUrl() + "/7");
        assertEquals(2, restTemplate.getForObject(getBaseUrl(), List.class).size());
    }

    @Test
    void deleteAllClothes() {
        assertNotEquals(0, restTemplate.getForObject(getBaseUrl(), List.class).size());
        restTemplate.delete(getBaseUrl());
        assertEquals(0, restTemplate.getForObject(getBaseUrl(), List.class).size());
    }
}