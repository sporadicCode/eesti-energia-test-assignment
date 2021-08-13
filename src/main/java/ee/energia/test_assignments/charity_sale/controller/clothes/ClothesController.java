package ee.energia.test_assignments.charity_sale.controller.clothes;

import ee.energia.test_assignments.charity_sale.exception.ProductNotFoundException;
import ee.energia.test_assignments.charity_sale.model.clothes.Clothes;
import ee.energia.test_assignments.charity_sale.repository.clothes.ClothesRepository;
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

@Api(value = "Clothes", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("/api/clothes")
public class ClothesController {
    private final ClothesRepository repository;

    @Autowired
    public ClothesController(ClothesRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Add clothes")
    @PostMapping
    Clothes saveClothes(@RequestBody @Valid Clothes clothes) {
        return repository.save(clothes);
    }

    @ApiOperation(value = "Get clothes by id")
    @GetMapping("/{id}")
    Clothes getClothesById(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @ApiOperation(value = "Get all clothes")
    @GetMapping
    List<Clothes> getAllClothes() {
        return repository.findAll();
    }

    @ApiOperation(value = "Update clothes by id")
    @PutMapping("/{id}")
    ResponseEntity<Clothes> updateClothes(@PathVariable int id, @Valid @RequestBody Clothes clothesDetails) {
        Clothes clothes = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        clothes.setImageUrl(clothesDetails.getImageUrl());
        clothes.setName(clothesDetails.getName());
        clothes.setPrice(clothesDetails.getPrice());
        clothes.setStock(clothesDetails.getStock());
        final Clothes updatedClothes = repository.save(clothes);
        return ResponseEntity.ok(updatedClothes);
    }

    @ApiOperation(value = "Delete clothes by id")
    @DeleteMapping("/{id}")
    void deleteClothesById(@PathVariable int id) {
        repository.deleteById(id);
    }

    @ApiOperation(value = "Delete all clothes")
    @DeleteMapping()
    void deleteAllClothes() {
        repository.deleteAll();
    }
}
