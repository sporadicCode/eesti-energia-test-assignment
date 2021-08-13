package ee.energia.test_assignments.charity_sale.controller.toys;

import ee.energia.test_assignments.charity_sale.exception.ProductNotFoundException;
import ee.energia.test_assignments.charity_sale.model.toys.Toy;
import ee.energia.test_assignments.charity_sale.repository.toys.ToysRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Toy", consumes = "application/json", produces = "application/json")
@RestController
@RequestMapping("/api/toys")
public class ToyController {
    private final ToysRepository repository;

    @Autowired
    public ToyController(ToysRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Add toy")
    @PostMapping
    Toy saveToy(@RequestBody @Valid Toy toy) {
        return repository.save(toy);
    }

    @ApiOperation(value = "Get toy by id")
    @GetMapping("/{id}")
    Toy findToyById(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @ApiOperation(value = "Get all toys")
    @GetMapping
    List<Toy> findAllToys() {
        return repository.findAll();
    }

    @ApiOperation(value = "Update toy by id")
    @PutMapping("/{id}")
    ResponseEntity<Toy> updateToy(@PathVariable int id, @Valid @RequestBody Toy toyDetails) {
        Toy toy = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        toy.setImageUrl(toyDetails.getImageUrl());
        toy.setName(toyDetails.getName());
        toy.setPrice(toyDetails.getPrice());
        toy.setStock(toyDetails.getStock());
        final Toy updatedToy = repository.save(toy);
        return ResponseEntity.ok(updatedToy);
    }

    @ApiOperation(value = "Delete toy by id")
    @DeleteMapping("/{id}")
    void deleteToyById(@PathVariable int id) {
        repository.deleteById(id);
    }

    @ApiOperation(value = "Delete all toys")
    @DeleteMapping
    void deleteAllToys() {
        repository.deleteAll();
    }
}
