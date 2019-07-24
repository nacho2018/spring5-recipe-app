package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {

        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Iterable<Recipe> recipes =  this.recipeRepository.findAll();
        Set<Recipe> listOfRecipes = new HashSet<>();

       if (null != recipes){
           recipes.forEach(recipe -> {
            listOfRecipes.add(recipe);
           });
       }
       return listOfRecipes;
    }
}
