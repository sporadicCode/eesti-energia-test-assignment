package ee.energia.test_assignments.charity_sale.data;

import ee.energia.test_assignments.charity_sale.model.clothes.Clothes;
import ee.energia.test_assignments.charity_sale.model.food.Food;
import ee.energia.test_assignments.charity_sale.model.toys.Toy;
import ee.energia.test_assignments.charity_sale.repository.clothes.ClothesRepository;
import ee.energia.test_assignments.charity_sale.repository.food.FoodRepository;
import ee.energia.test_assignments.charity_sale.repository.toys.ToysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DatabaseInit {

    private final FoodRepository foodRepository;
    private final ClothesRepository clothesRepository;
    private final ToysRepository toysRepository;

    @Autowired
    public DatabaseInit(ToysRepository toysRepository,
                        ClothesRepository clothesRepository,
                        FoodRepository foodRepository) {
        this.toysRepository = toysRepository;
        this.clothesRepository = clothesRepository;
        this.foodRepository = foodRepository;
    }

    private void addFood(String name, String imageUrl, BigDecimal price, int stock) {
        Food food = new Food();
        food.setName(name);
        food.setImageUrl(imageUrl);
        food.setPrice(price);
        food.setStock(stock);
        foodRepository.save(food);
    }

    private void addClothes(String name, String imageUrl, BigDecimal price, int stock) {
        Clothes clothes = new Clothes();
        clothes.setName(name);
        clothes.setImageUrl(imageUrl);
        clothes.setPrice(price);
        clothes.setStock(stock);
        clothesRepository.save(clothes);
    }

    private void addToy(String name, String imageUrl, BigDecimal price, int stock) {
        Toy toy = new Toy();
        toy.setName(name);
        toy.setImageUrl(imageUrl);
        toy.setPrice(price);
        toy.setStock(stock);
        toysRepository.save(toy);
    }

    @PostConstruct
    private void postConstruct() {

        addFood("muffin", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fpngimg.com%2Fuploads%2Fmuffin%2Fmuffin_PNG2.png", BigDecimal.valueOf(1.00), 36);
        addFood("cake pop", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.downloadclipart.net%2Flarge%2Fcake-pop-png-transparent-image.png", BigDecimal.valueOf(1.35), 24);
        addFood("brownie", "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.benjerry.com%2Ffiles%2Flive%2Fsites%2Fsystemsite%2Ffiles%2Fwhats-new%2F3878-what-chunk%2F3-chocolate-brownie.png", BigDecimal.valueOf(0.65), 48);
        addFood("apple tart", "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2F1.bp.blogspot.com%2F-CBB0EaxO1jE%2FVVcmJ7XtHiI%2FAAAAAAAAQH8%2FxF5gvsDJgpM%2Fs1600%2FApple-Pie.png", BigDecimal.valueOf(1.50), 60);
        addFood("water", "https://pngimg.com/uploads/water_bottle/water_bottle_PNG98959.png", BigDecimal.valueOf(1.50), 60);

        addClothes("shirt", "https://pngimg.com/uploads/dress_shirt/dress_shirt_PNG8117.png", BigDecimal.valueOf(2.00), 0);
        addClothes("pants", "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fpurepng.com%2Fpublic%2Fuploads%2Flarge%2Fpurepng.com-mens-jeansgarmentlower-bodydenimjeansnavy-blue-1421526362794kpmhb.png", BigDecimal.valueOf(3.00), 0);
        addClothes("jacket", "https://pngimg.com/uploads/jacket/jacket_PNG8058.png", BigDecimal.valueOf(4.00), 0);

        addToy("toy", "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.freepngimg.com%2Fthumb%2Ftoy%2F33445-9-wooden-toy-transparent-background.png", BigDecimal.valueOf(1.00), 0);
    }

}
