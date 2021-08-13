package ee.energia.test_assignments.charity_sale.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", columnDefinition = "serial")
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 255, message = "Name should be no longer than 255 characters")
    private String name;

    @Column(nullable = false)
    @JsonProperty("image_url")
    @NotBlank
    @Pattern(regexp = "^(https?|http|www)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    @Size(max = 255, message = "Image URL should be no longer than 255 characters")
    private String imageUrl;

    @Column(nullable = false)
    @NotNull
    @DecimalMin(value = "0.00", message = "Price cannot be lower than 0")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = "Stock cannot be lower than 0")
    private Integer stock;

    public Category getCategory() {
        return Category.GENERIC;
    }
}
