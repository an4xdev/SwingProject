package edu.uws.ii.project.configurations;

import edu.uws.ii.project.Repositories.*;
import edu.uws.ii.project.domain.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Configuration
public class RepositoryInitializer {

    private final CategoryRepository categoryRepository;
    private final DifficultyRepository difficultyRepository;
    private final EventRepository eventRepository;
    private final RoleRepository roleRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;
    private final FavouriteRepository favouriteRepository;
    private final RecipeHistoryRepository recipeHistoryRepository;
    private final StepRepository stepRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public RepositoryInitializer(CategoryRepository categoryRepository, DifficultyRepository difficultyRepository, EventRepository eventRepository, RoleRepository roleRepository, IngredientRepository ingredientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RecipeRepository recipeRepository, CommentRepository commentRepository, FavouriteRepository favouriteRepository, RecipeHistoryRepository recipeHistoryRepository, StepRepository stepRepository, RatingRepository ratingRepository) {
        this.categoryRepository = categoryRepository;
        this.difficultyRepository = difficultyRepository;
        this.eventRepository = eventRepository;
        this.roleRepository = roleRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.recipeRepository = recipeRepository;
        this.commentRepository = commentRepository;
        this.favouriteRepository = favouriteRepository;
        this.recipeHistoryRepository = recipeHistoryRepository;
        this.stepRepository = stepRepository;
        this.ratingRepository = ratingRepository;
    }

    @Bean
    InitializingBean init() {
        return () -> {
            // Initialize difficulties
            Difficulty easy = new Difficulty("Easy");
            Difficulty medium = new Difficulty("Medium");
            Difficulty hard = new Difficulty("Hard");
            if (difficultyRepository.count() == 0) {
                difficultyRepository.saveAll(List.of(easy, medium, hard));
            }

            // Initialize categories
            Category breakfast = new Category("Breakfast");
            Category dinner = new Category("Dinner");
            Category dessert = new Category("Dessert");
            Category snack = new Category("Snack");
            if (categoryRepository.count() == 0) {
                categoryRepository.saveAll(List.of(breakfast, dinner, dessert, snack));
            }

            // Initialize roles
            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");
            if (roleRepository.count() == 0) {
                roleRepository.saveAll(List.of(adminRole, userRole));
            }

            // Initialize users
            User admin = new User("admin", passwordEncoder.encode("admin"), "admin@example.com");
            admin.getRoles().add(adminRole);

            User user1 = new User("user1", passwordEncoder.encode("user1"), "user1@example.com");
            user1.getRoles().add(userRole);

            User user2 = new User("user2", passwordEncoder.encode("user2"), "user2@exampl.ecom");
            user2.getRoles().add(userRole);

            User user3 = new User("user3", passwordEncoder.encode("user3"), "user3@exampl.ecom");
            user3.getRoles().add(userRole);

            if (userRepository.count() == 0) {
                userRepository.saveAll(List.of(admin, user1, user2, user3));
            }

            // Initialize events
            Event casual = new Event("Casual");
            Event birthday = new Event("Birthday");
            Event anniversary = new Event("Anniversary");
            Event christmas = new Event("Christmas");
            if (eventRepository.count() == 0) {
                eventRepository.saveAll(List.of(casual, birthday, anniversary, christmas));
            }

            // Initialize ingredients
            Ingredient flour = new Ingredient("Flour");
            Ingredient sugar = new Ingredient("Sugar");
            Ingredient eggs = new Ingredient("Eggs");
            Ingredient butter = new Ingredient("Butter");
            Ingredient milk = new Ingredient("Milk");
            Ingredient salt = new Ingredient("Salt");
            Ingredient pasta = new Ingredient("Pasta");
            Ingredient bread = new Ingredient("Bread");
            Ingredient chicken = new Ingredient("Chicken");
            Ingredient beef = new Ingredient("Beef");
            Ingredient potatoes = new Ingredient("Potatoes");
            Ingredient salami = new Ingredient("Salami");
            Ingredient pineapple = new Ingredient("Pineapple");
            Ingredient tomatoSauce = new Ingredient("Tomato Sauce");
            Ingredient mozzarella = new Ingredient("Mozzarella");
            Ingredient strawberries = new Ingredient("Strawberries");
            Ingredient ham = new Ingredient("Ham");
            Ingredient cheese = new Ingredient("Cheese");

            if (ingredientRepository.count() == 0) {
                ingredientRepository.saveAll(List.of(flour, sugar, eggs, butter, milk, salt, pasta, bread, chicken, beef, potatoes, salami, pineapple, tomatoSauce, mozzarella, strawberries, ham, cheese));
            }

            // Initialize times

            Time time1 = new Time(20, 30, 50);
            Time time2 = new Time(30, 60, 90);
            Time time3 = new Time(30, 60, 90);
            Time time4 = new Time(10, 20, 30);
            Time time5 = new Time(15, 45, 60);
            Time time6 = new Time(5, 55, 60);
            Time time7 = new Time(60, 60, 120);
            Time time8 = new Time(10, 25, 35);
            Time time9 = new Time(25, 35, 60);

            // Initialize recipes

            // photo `chmura.jpg` got from: https://picsum.photos/ (MIT License)

            Recipe pancakes = new Recipe("Pancakes", "Pancakes are a breakfast staple and are loved by many. They are easy to make and can be customized to your liking.", "/user_images/chmura.jpg", time1, false, LocalDateTime.now(), user1, new HashSet<>(List.of(sugar, milk, flour, eggs)), breakfast, easy, new HashSet<>(List.of(casual)));

            Recipe strawberryCake = new Recipe("Strawberry Cake", "Strawberry Cake is great cake for special events", "/user_images/chmura.jpg", time2, true, LocalDateTime.now(), user1 ,new HashSet<>(List.of(sugar, milk, flour,eggs, strawberries)), dessert, medium, new HashSet<>(List.of(birthday, anniversary, christmas)));

            Recipe spaghettiCarbonara = new Recipe("Spaghetti carbonara", "Great italian dish for dinner.", "/user_images/chmura.jpg", time3, false, LocalDateTime.now(), user1, new HashSet<>(List.of(pasta, milk, beef)), dinner, hard, new HashSet<>(List.of(casual)));

            Recipe sandwich = new Recipe("Sandwich", "Simple sandwich for quick breakfast.", "/user_images/chmura.jpg", time4, false, LocalDateTime.now(), user1, new HashSet<>(List.of(bread, cheese, ham)), breakfast, easy, new HashSet<>(List.of(casual)));

            Recipe pomodoroSoup = new Recipe("Pomodoro Soup", "Simple pomodoro soup from broth from yesterday", "/user_images/chmura.jpg", time5, false, LocalDateTime.now(), user2 ,new HashSet<>(List.of(tomatoSauce, pasta)), dinner, medium, new HashSet<>(List.of(casual)));

            Recipe beefWithPotatoes = new Recipe("Beef with potatoes", "Classic dinner for casual and specials events.", "/user_images/chmura.jpg", time6, false, LocalDateTime.now(), user2, new HashSet<>(List.of(potatoes, beef)), dinner, hard, new HashSet<>(List.of(casual, birthday, anniversary)));

            Recipe hawaiianPizza = new Recipe("Hawaiian pizza", "Only true pizza, change my mind.", "/user_images/chmura.jpg", time7, true, LocalDateTime.now(), user3 ,new HashSet<>(List.of(flour, eggs, pineapple, ham)), dinner, medium, new HashSet<>(List.of(casual, birthday)));

            Recipe pizzaSalami = new Recipe("Pizza salami", "Classical pizza great for all events.", "/user_images/chmura.jpg", time8, true, LocalDateTime.now(), user3, new HashSet<>(List.of(flour, eggs, salami, tomatoSauce)), dinner, hard, new HashSet<>(List.of(casual, birthday, anniversary)));

            Recipe bar = new Recipe("Bar", "Simple bar for quick snack.", "/user_images/chmura.jpg", time9, false, LocalDateTime.now(), user3 ,new HashSet<>(List.of(flour, eggs, sugar, butter)), snack, easy, new HashSet<>(List.of(casual)));

            if (recipeRepository.count() == 0) {
                recipeRepository.saveAll(List.of(pancakes, strawberryCake, spaghettiCarbonara, sandwich, pomodoroSoup, beefWithPotatoes, hawaiianPizza, pizzaSalami, bar));
            }

            // Initialize comments
            Comment comment1 = new Comment("This recipe is amazing!", pancakes, user2);
            Comment comment2 = new Comment("I love this recipe!", pancakes, user3);
            Comment comment3 = new Comment("This recipe is delicious!", spaghettiCarbonara, user1);
            Comment comment4 = new Comment("This recipe is great!", spaghettiCarbonara, user2);
            Comment comment5 = new Comment("I love this recipe!", pomodoroSoup, user3);
            Comment comment6 = new Comment("This recipe is delicious!", hawaiianPizza, user1);
            Comment comment7 = new Comment("This recipe is amazing!", hawaiianPizza, user2);
            Comment comment8 = new Comment("I love this recipe!", bar, user3);
            Comment comment9 = new Comment("This recipe is delicious!", bar, user1);

            if (commentRepository.count() == 0) {
                commentRepository.saveAll(List.of(comment1, comment2, comment3, comment4, comment5, comment6, comment7, comment8, comment9));
            }

            // Initialize favourites
            Favourite favourite1 = new Favourite(user1, pancakes);
            Favourite favourite2 = new Favourite(user1, strawberryCake);
            Favourite favourite3 = new Favourite(user1, pomodoroSoup);
            Favourite favourite4 = new Favourite(user1, hawaiianPizza);
            Favourite favourite5 = new Favourite(user2, bar);
            Favourite favourite6 = new Favourite(user2, spaghettiCarbonara);
            Favourite favourite7 = new Favourite(user3, pizzaSalami);
            Favourite favourite8 = new Favourite(user3, pomodoroSoup);
            Favourite favourite9 = new Favourite(user3, hawaiianPizza);

            if (favouriteRepository.count() == 0) {
                favouriteRepository.saveAll(List.of(favourite1, favourite2, favourite3, favourite4, favourite5, favourite6, favourite7, favourite8, favourite9));
            }

            // Initialize recipe histories
            RecipeHistory recipeHistory1 = new RecipeHistory(user1, pancakes);
            RecipeHistory recipeHistory2 = new RecipeHistory(user1, strawberryCake);
            RecipeHistory recipeHistory3 = new RecipeHistory(user1, pomodoroSoup);
            RecipeHistory recipeHistory4 = new RecipeHistory(user1, hawaiianPizza);
            RecipeHistory recipeHistory5 = new RecipeHistory(user2, bar);
            RecipeHistory recipeHistory6 = new RecipeHistory(user2, spaghettiCarbonara);
            RecipeHistory recipeHistory7 = new RecipeHistory(user3, pizzaSalami);
            RecipeHistory recipeHistory8 = new RecipeHistory(user3, pomodoroSoup);
            RecipeHistory recipeHistory9 = new RecipeHistory(user3, hawaiianPizza);

            if (recipeHistoryRepository.count() == 0) {
                recipeHistoryRepository.saveAll(List.of(recipeHistory1, recipeHistory2, recipeHistory3, recipeHistory4, recipeHistory5, recipeHistory6, recipeHistory7, recipeHistory8, recipeHistory9));
            }

            // Initialize steps
            Step step1 = new Step("Mix all ingredients together", pancakes);
            Step step2 = new Step("Cook on a pan", pancakes);
            Step step3 = new Step("Serve with your favorite toppings", pancakes);

            Step step4 = new Step("Mix all ingredients together", strawberryCake);
            Step step5 = new Step("Bake in the oven", strawberryCake);
            Step step6 = new Step("Put to fridge", strawberryCake);

            Step step7 = new Step("Mix all ingredients together", spaghettiCarbonara);
            Step step8 = new Step("Cook on a pan", spaghettiCarbonara);
            Step step9 = new Step("Serve on plate with pasta", spaghettiCarbonara);

            Step step10 = new Step("Prepare ingredients", sandwich);
            Step step11 = new Step("Combine to sandwich", sandwich);
            Step step12 = new Step("Serve on plate", sandwich);

            Step step13 = new Step("Prepare ingredients", pomodoroSoup);
            Step step14 = new Step("Put all to pot", pomodoroSoup);
            Step step15 = new Step("Serve on plate", pomodoroSoup);

            Step step16 = new Step("Prepare ingredients", beefWithPotatoes);
            Step step17 = new Step("Cook on a pan", beefWithPotatoes);
            Step step18 = new Step("Prepare potatoes", beefWithPotatoes);
            Step step19 = new Step("Serve on plate", beefWithPotatoes);

            Step step20 = new Step("Prepare ingredients", hawaiianPizza);
            Step step21 = new Step("Prepare dough", hawaiianPizza);
            Step step22 = new Step("Put all ingredients on dough", hawaiianPizza);
            Step step23 = new Step("Bake in the oven", hawaiianPizza);

            Step step24 = new Step("Prepare ingredients", pizzaSalami);
            Step step25 = new Step("Prepare dough", pizzaSalami);
            Step step26 = new Step("Put all ingredients on dough", pizzaSalami);
            Step step27 = new Step("Bake in the oven", pizzaSalami);

            Step step28 = new Step("Mix all ingredients together", bar);
            Step step29 = new Step("Bake in the oven", bar);
            Step step30 = new Step("Eat when it cools down", bar);

            if(stepRepository.count() == 0) {
                stepRepository.saveAll(List.of(step1, step2, step3, step4, step5, step6, step7, step8, step9, step10, step11, step12, step13, step14, step15, step16, step17, step18, step19, step20, step21, step22, step23, step24, step25, step26, step27, step28, step29, step30));
            }

            Rating rating1 = new Rating( user1, pancakes, 5.23F);
            Rating rating2 = new Rating( user2, pancakes, 4.23F);
            Rating rating3 = new Rating( user3, pancakes, 6.23F);
            Rating rating4 = new Rating( user1, strawberryCake, 5.23F);
            Rating rating5 = new Rating( user2, strawberryCake, 4.23F);
            Rating rating6 = new Rating( user3, strawberryCake, 5.23F);
            Rating rating7 = new Rating( user1, spaghettiCarbonara, 5.23F);
            Rating rating8 = new Rating( user2, spaghettiCarbonara, 8.23F);
            Rating rating9 = new Rating( user3, spaghettiCarbonara, 3.23F);
            Rating rating10 = new Rating( user1, sandwich, 5.23F);
            Rating rating11 = new Rating( user2, sandwich, 5.23F);
            Rating rating12 = new Rating( user3, sandwich, 3.23F);
            Rating rating13 = new Rating( user1, pomodoroSoup, 5.23F);
            Rating rating14 = new Rating( user2, pomodoroSoup, 8.23F);
            Rating rating15 = new Rating( user3, pomodoroSoup, 3.23F);
            Rating rating16 = new Rating( user1, beefWithPotatoes, 9.23F);
            Rating rating17 = new Rating( user2, beefWithPotatoes, 4.23F);
            Rating rating18 = new Rating( user3, beefWithPotatoes, 3.23F);
            Rating rating19 = new Rating( user1, hawaiianPizza, 7.23F);
            Rating rating20 = new Rating( user2, hawaiianPizza, 6.23F);
            Rating rating21 = new Rating( user3, hawaiianPizza, 9.23F);
            Rating rating22 = new Rating( user1, pizzaSalami, 5.23F);
            Rating rating23 = new Rating( user2, pizzaSalami, 8.23F);
            Rating rating24 = new Rating( user3, pizzaSalami, 3.23F);
            Rating rating25 = new Rating( user1, bar, 5.23F);
            Rating rating26 = new Rating( user2, bar, 8.23F);
            Rating rating27 = new Rating( user3, bar, 3.23F);

            if(ratingRepository.count() == 0) {
                ratingRepository.saveAll(List.of(rating1, rating2, rating3, rating4, rating5, rating6, rating7, rating8, rating9, rating10, rating11, rating12, rating13, rating14, rating15, rating16, rating17, rating18, rating19, rating20, rating21, rating22, rating23, rating24, rating25, rating26, rating27));
            }
        };
    }
}
