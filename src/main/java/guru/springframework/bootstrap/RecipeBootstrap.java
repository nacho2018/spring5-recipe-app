package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMesureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {



    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMesureRepository unitOfMesureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMesureRepository unitOfMesureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMesureRepository = unitOfMesureRepository;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        this.recipeRepository.saveAll(getRecipies());
    }

    private List<Recipe> getRecipies(){

        List<Recipe> recipies = new ArrayList<>();

        Optional<Category> americanCategoryOptional = this.categoryRepository.findByDescription("American");
        if (!americanCategoryOptional.isPresent()){
            throw new RuntimeException();
        }
        Category americanCategory = americanCategoryOptional.get();
        Optional<Category> mexicanCategoryOptional = this.categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException();
        }
        Category mexicanCategory = mexicanCategoryOptional.get();

        //getting uom
        Optional<UnitOfMeasure> uomTeaspoonOptional = this.unitOfMesureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> uomTablespoonOptional = this.unitOfMesureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> uomDashOptional = this.unitOfMesureRepository.findByDescription("Dash");
        Optional<UnitOfMeasure> uomCupOptional = this.unitOfMesureRepository.findByDescription("Cup");
        Optional<UnitOfMeasure> uomEachOptional = this.unitOfMesureRepository.findByDescription("Each");

        if (!uomTeaspoonOptional.isPresent()){
            throw new RuntimeException();
        }
        UnitOfMeasure uomTeaspoon = uomTeaspoonOptional.get();

        if (!uomTablespoonOptional.isPresent()){
            throw new RuntimeException();
        }
        UnitOfMeasure uomTablespoon = uomTablespoonOptional.get();

        if (!uomDashOptional.isPresent()){
            throw new RuntimeException();
        }
        UnitOfMeasure uomDash = uomDashOptional.get();

        if (!uomCupOptional.isPresent()){
            throw new RuntimeException();
        }
        UnitOfMeasure uomCup = uomCupOptional.get();


        if (!uomEachOptional.isPresent()){
            throw new RuntimeException();
        }
        UnitOfMeasure uomEach = uomEachOptional.get();

        Recipe recipe = new Recipe();
        recipe.setDescription("Perfect Guacamole Recipe");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed.\n" +
                "Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon.\n" +
               "Place in a bowl. 2 Mash with a fork: Using a fork, roughly mash the avocado.\n " +
                "(Don't overdo it! The guacamole should be a little chunky.)" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice.\n" +
                "The acid in the lime juice will provide some balance to the richness of the avocado and \n" +
                "will help delay the avocados from turning brown.\n " +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness.\n" + "" +
                " So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients.\n" +
                " Start with this recipe and adjust to your taste.\n" + "4 Cover with plastic and chill to store: Place plastic\n" + " wrap on the surface of the guacamole cover it and to prevent air reaching it.\n" +
                " (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor,\n" +
                " so if you want to add chopped tomato to your guacamole, add it just before serving.");
        Notes recipeNotes = new Notes();
        recipeNotes.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do\n " +
                "not touch your eyes or the area near your eyes with your hands for several hours.");
        recipe.setNotes(recipeNotes);
        recipe.setCookTime(0);
        recipe.setPrepTime(10);
        recipe.setSource("Food Literacy Center");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        recipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), uomEach));
        recipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(0.5), uomTeaspoon));
        recipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(1), uomTablespoon));
        recipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), uomTablespoon));
        recipe.addIngredient(new Ingredient("serrano chiles,steams and seeds removed,minced", new BigDecimal(1), uomEach));
        recipe.addIngredient(new Ingredient("cilantro(leaves and tender stems, finely chopped", new BigDecimal(2), uomTablespoon));
        recipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(1), uomDash));
        recipe.addIngredient(new Ingredient("ripe tomato,seeds and pulp removed,chopper", new BigDecimal(0.5), uomEach));

        recipe.getCategories().add(americanCategory);
        recipe.getCategories().add(mexicanCategory);

        recipies.add(recipe);

        return recipies;

    }
}
