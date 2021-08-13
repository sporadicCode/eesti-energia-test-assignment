package ee.energia.test_assignments.charity_sale.controller.food;

import ee.energia.test_assignments.charity_sale.exception.ProductNotFoundException;
import ee.energia.test_assignments.charity_sale.model.food.Food;
import ee.energia.test_assignments.charity_sale.repository.food.FoodRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Food", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodRepository repository;

    @Autowired
    public FoodController(FoodRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Add food")
    @PostMapping
    Food saveFood(@RequestBody @Valid Food food) {
        return repository.save(food);
    }

    @ApiOperation(value = "Get food by id")
    @GetMapping("/{id}")
    Food findFoodById(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @ApiOperation(value = "Get all food")
    @GetMapping
    List<Food> findAllFood() {
        return repository.findAll();
    }

    @ApiOperation(value = "Update food by id")
    @PutMapping("/{id}")
    ResponseEntity<Food> updateFood(@PathVariable int id, @Valid @RequestBody Food foodDetails) {
        Food food = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        food.setImageUrl(foodDetails.getImageUrl());
        food.setName(foodDetails.getName());
        food.setPrice(foodDetails.getPrice());
        food.setStock(foodDetails.getStock());
        final Food updatedFood = repository.save(food);
        return ResponseEntity.ok(updatedFood);
    }

    @ApiOperation(value = "Delete food by id")
    @DeleteMapping("/{id}")
    void deleteFoodById(@PathVariable int id) {
        repository.deleteById(id);
    }

    @ApiOperation(value = "Delete all food")
    @DeleteMapping
    void deleteAllFood() {
        repository.deleteAll();
    }
}
