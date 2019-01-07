package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {

        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipes() {
        Iterable<Recipe> recipes =  this.recipeRepository.findAll();
        List<Recipe> listOfRecipes = new ArrayList<>();

       if (null != recipes){
           recipes.forEach(recipe -> {
            listOfRecipes.add(recipe);
           });
       }
       return listOfRecipes;
    }
}
