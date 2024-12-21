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

    @Autowired
    public RepositoryInitializer(CategoryRepository categoryRepository, DifficultyRepository difficultyRepository, EventRepository eventRepository, RoleRepository roleRepository, IngredientRepository ingredientRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RecipeRepository recipeRepository, CommentRepository commentRepository, FavouriteRepository favouriteRepository, RecipeHistoryRepository recipeHistoryRepository, StepRepository stepRepository) {
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
    }

    @Bean
    InitializingBean init() {
        return () -> {
            // Initialize difficulties
            Difficulty easy = new Difficulty("Easy", 1);
            Difficulty medium = new Difficulty("Medium", 2);
            Difficulty hard = new Difficulty("Hard", 3);
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

            // Initialize recipes
            Recipe pancakes = new Recipe("Pancakes", "Pancakes are a breakfast staple and are loved by many. They are easy to make and can be customized to your liking.", "/user_images/chmura.jpg", 20, 8.75f, false, LocalDateTime.now(), user1, new HashSet<>(List.of(sugar, milk, flour, eggs)), breakfast, easy, new HashSet<>(List.of(casual)));

            Recipe strawberryCake = new Recipe("Strawberry Cake", "Strawberry Cake is great cake for special events", "/user_images/chmura.jpg", 30, 6.87f, true, LocalDateTime.now(), user2 ,new HashSet<>(List.of(sugar, milk, flour,eggs, strawberries)), dessert, medium, new HashSet<>(List.of(birthday, anniversary, christmas)));

            Recipe spaghettiCarbonara = new Recipe("Spaghetti carbonara", "Great italian dish for dinner.", "/user_images/chmura.jpg", 30, 5.20f, false, LocalDateTime.now(), user3, new HashSet<>(List.of(pasta, milk, beef)), dinner, hard, new HashSet<>(List.of(casual)));

            Recipe sandwich = new Recipe("Sandwich", "Simple sandwich for quick breakfast.", "/user_images/chmura.jpg", 30, 4.20f, false, LocalDateTime.now(), user3, new HashSet<>(List.of(bread, cheese, ham)), breakfast, easy, new HashSet<>(List.of(casual)));

            Recipe pomodoroSoup = new Recipe("Pomodoro Soup", "Simple pomodoro soup from broth from yesterday", "/user_images/chmura.jpg", 30, 7.65f, false, LocalDateTime.now(), user1 ,new HashSet<>(List.of(tomatoSauce, pasta)), dinner, medium, new HashSet<>(List.of(casual)));

            Recipe beefWithPotatoes = new Recipe("Beef with potatoes", "Classic dinner for casual and specials events.", "/user_images/chmura.jpg", 30, 9.81f, false, LocalDateTime.now(), user2, new HashSet<>(List.of(potatoes, beef)), dinner, hard, new HashSet<>(List.of(casual, birthday, anniversary)));

            Recipe hawaiianPizza = new Recipe("Hawaiian pizza", "Only true pizza, change my mind.", "/user_images/chmura.jpg", 30, 9.99f, true, LocalDateTime.now(), user2 ,new HashSet<>(List.of(flour, eggs, pineapple, ham)), dinner, medium, new HashSet<>(List.of(casual, birthday)));

            Recipe pizzaSalami = new Recipe("Pizza salami", "Classical pizza great for all events.", "/user_images/chmura.jpg", 30, 2.13f, true, LocalDateTime.now(), user3, new HashSet<>(List.of(flour, eggs, salami, tomatoSauce)), dinner, hard, new HashSet<>(List.of(casual, birthday, anniversary)));

            Recipe bar = new Recipe("Bar", "Simple bar for quick snack.", "/user_images/chmura.jpg", 30, 3.17f, false, LocalDateTime.now(), user1 ,new HashSet<>(List.of(flour, eggs, sugar, butter)), snack, easy, new HashSet<>(List.of(casual)));

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
            Favourite favourite1 = new Favourite(user2, pancakes);
            Favourite favourite2 = new Favourite(user1, strawberryCake);
            Favourite favourite3 = new Favourite(user3, pomodoroSoup);
            Favourite favourite4 = new Favourite(user1, hawaiianPizza);
            Favourite favourite5 = new Favourite(user2, bar);
            Favourite favourite6 = new Favourite(user3, spaghettiCarbonara);
            Favourite favourite7 = new Favourite(user1, pizzaSalami);
            Favourite favourite8 = new Favourite(user2, pomodoroSoup);
            Favourite favourite9 = new Favourite(user3, hawaiianPizza);

            if (favouriteRepository.count() == 0) {
                favouriteRepository.saveAll(List.of(favourite1, favourite2, favourite3, favourite4, favourite5, favourite6, favourite7, favourite8, favourite9));
            }

            // Initialize recipe histories
            RecipeHistory recipeHistory1 = new RecipeHistory(user2, pancakes);
            RecipeHistory recipeHistory2 = new RecipeHistory(user1, strawberryCake);
            RecipeHistory recipeHistory3 = new RecipeHistory(user3, pomodoroSoup);
            RecipeHistory recipeHistory4 = new RecipeHistory(user1, hawaiianPizza);
            RecipeHistory recipeHistory5 = new RecipeHistory(user2, bar);
            RecipeHistory recipeHistory6 = new RecipeHistory(user3, spaghettiCarbonara);
            RecipeHistory recipeHistory7 = new RecipeHistory(user1, pizzaSalami);
            RecipeHistory recipeHistory8 = new RecipeHistory(user2, pomodoroSoup);
            RecipeHistory recipeHistory9 = new RecipeHistory(user3, hawaiianPizza);

            if (recipeHistoryRepository.count() == 0) {
                recipeHistoryRepository.saveAll(List.of(recipeHistory1, recipeHistory2, recipeHistory3, recipeHistory4, recipeHistory5, recipeHistory6, recipeHistory7, recipeHistory8, recipeHistory9));
            }

            // Initialize steps
            Step step1 = new Step("Mix all ingredients together", 1, pancakes);
            Step step2 = new Step("Cook on a pan", 2, pancakes);
            Step step3 = new Step("Serve with your favorite toppings", 3, pancakes);

            Step step4 = new Step("Mix all ingredients together", 1, strawberryCake);
            Step step5 = new Step("Bake in the oven", 2, strawberryCake);
            Step step6 = new Step("Put to fridge", 3, strawberryCake);

            Step step7 = new Step("Mix all ingredients together", 1, spaghettiCarbonara);
            Step step8 = new Step("Cook on a pan", 2, spaghettiCarbonara);
            Step step9 = new Step("Serve on plate with pasta", 3, spaghettiCarbonara);

            Step step10 = new Step("Prepare ingredients", 1, sandwich);
            Step step11 = new Step("Combine to sandwich", 2, sandwich);
            Step step12 = new Step("Serve on plate", 3, sandwich);

            Step step13 = new Step("Prepare ingredients", 1, pomodoroSoup);
            Step step14 = new Step("Put all to pot", 2, pomodoroSoup);
            Step step15 = new Step("Serve on plate", 3, pomodoroSoup);

            Step step16 = new Step("Prepare ingredients", 1, beefWithPotatoes);
            Step step17 = new Step("Cook on a pan", 2, beefWithPotatoes);
            Step step18 = new Step("Prepare potatoes", 3, beefWithPotatoes);
            Step step19 = new Step("Serve on plate", 4, beefWithPotatoes);

            Step step20 = new Step("Prepare ingredients", 1, hawaiianPizza);
            Step step21 = new Step("Prepare dough", 2, hawaiianPizza);
            Step step22 = new Step("Put all ingredients on dough", 3, hawaiianPizza);
            Step step23 = new Step("Bake in the oven", 4, hawaiianPizza);

            Step step24 = new Step("Prepare ingredients", 1, pizzaSalami);
            Step step25 = new Step("Prepare dough", 2, pizzaSalami);
            Step step26 = new Step("Put all ingredients on dough", 3, pizzaSalami);
            Step step27 = new Step("Bake in the oven", 4, pizzaSalami);

            Step step28 = new Step("Mix all ingredients together", 1, bar);
            Step step29 = new Step("Bake in the oven", 2, bar);
            Step step30 = new Step("Eat when it cools down", 3, bar);

            if(stepRepository.count() == 0) {
                stepRepository.saveAll(List.of(step1, step2, step3, step4, step5, step6, step7, step8, step9, step10, step11, step12, step13, step14, step15, step16, step17, step18, step19, step20, step21, step22, step23, step24, step25, step26, step27, step28, step29, step30));
            }
        };
    }
}
