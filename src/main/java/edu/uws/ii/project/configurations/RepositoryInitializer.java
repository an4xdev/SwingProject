package edu.uws.ii.project.configurations;

import edu.uws.ii.project.Repositories.*;
import edu.uws.ii.project.domain.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class RepositoryInitializer {

    private final CategoryRepository categoryRepository;
    private final DifficultyRepository difficultyRepository;
    private final EventRepository eventRepository;
    private final RoleRepository roleRepository;
    private final TagRepository tagRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RepositoryInitializer(CategoryRepository categoryRepository, DifficultyRepository difficultyRepository, EventRepository eventRepository, RoleRepository roleRepository, TagRepository tagRepository, IngredientRepository ingredientRepository) {
        this.categoryRepository = categoryRepository;
        this.difficultyRepository = difficultyRepository;
        this.eventRepository = eventRepository;
        this.roleRepository = roleRepository;
        this.tagRepository = tagRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Bean
    InitializingBean init() {
        return () -> {
            if (difficultyRepository.count() == 0) {
                Difficulty difficulty1 = new Difficulty("Easy", 1);
                Difficulty difficulty2 = new Difficulty("Medium", 2);
                Difficulty difficulty3 = new Difficulty("Hard", 3);
                difficultyRepository.save(difficulty1);
                difficultyRepository.save(difficulty2);
                difficultyRepository.save(difficulty3);
            }

            if (categoryRepository.count() == 0) {
                Category category1 = new Category("Breakfast");
                Category category2 = new Category("Lunch");
                Category category3 = new Category("Dinner");
                Category category4 = new Category("Dessert");
                categoryRepository.save(category1);
                categoryRepository.save(category2);
                categoryRepository.save(category3);
                categoryRepository.save(category4);
            }

            if (roleRepository.count() == 0) {
                Role role1 = new Role("Admin");
                Role role2 = new Role("User");
                roleRepository.save(role1);
                roleRepository.save(role2);
            }

            if (tagRepository.count() == 0) {
                Tag tag1 = new Tag("Vegetarian");
                Tag tag2 = new Tag("Vegan");
                Tag tag3 = new Tag("Gluten Free");
                Tag tag4 = new Tag("Dairy Free");
                tagRepository.save(tag1);
                tagRepository.save(tag2);
                tagRepository.save(tag3);
                tagRepository.save(tag4);
            }

            if (eventRepository.count() == 0) {
                Event event1 = new Event("Birthday", null);
                Event event2 = new Event("Anniversary", null);
                Event event3 = new Event("Christmas", LocalDate.of(2021, 12, 25));
                eventRepository.save(event1);
                eventRepository.save(event2);
                eventRepository.save(event3);
            }

            if (ingredientRepository.count() == 0) {
                Ingredient ingredient1 = new Ingredient("Flour");
                Ingredient ingredient2 = new Ingredient("Sugar");
                Ingredient ingredient3 = new Ingredient("Eggs");
                Ingredient ingredient4 = new Ingredient("Butter");
                ingredientRepository.save(ingredient1);
                ingredientRepository.save(ingredient2);
                ingredientRepository.save(ingredient3);
                ingredientRepository.save(ingredient4);
            }
        };
    }
}
