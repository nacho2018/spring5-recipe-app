package guru.springframework.services;

import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.*;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import guru.springframework.domain.Recipe;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.Assert.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    final Long id = 1L;

    @Mock
    RecipeRepository recipeRepository;

    RecipeCommandToRecipe recipeCommandToRecipe;

    RecipeToRecipeCommand recipeToRecipeCommand;



    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        //recipeCommandToRecipe attributes
        CategoryCommandToCategory categoryConverter = new CategoryCommandToCategory();
        UnitOfMeasureCommandToUnitOfMeasure uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
        IngredientCommandToIngredient ingredientConverter = new IngredientCommandToIngredient(uomConverter);
        NotesCommandToNotes notesConverter = new NotesCommandToNotes();

        recipeCommandToRecipe = new RecipeCommandToRecipe(categoryConverter, ingredientConverter, notesConverter);

        //recipeToRecipeCommand attributes
        CategoryToCategoryCommand categoryConverterBis = new CategoryToCategoryCommand();
        UnitOfMeasureToUnitOfMeasureCommand uomConverterBis = new UnitOfMeasureToUnitOfMeasureCommand();
        IngredientToIngredientCommand ingredientConverterBis = new IngredientToIngredientCommand(uomConverterBis);
        NotesToNotesCommand notesConverterBis = new NotesToNotesCommand();
        recipeToRecipeCommand = new RecipeToRecipeCommand(categoryConverterBis, ingredientConverterBis, notesConverterBis);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }



    @Test
    public void getRecipes() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
    @Test
    public void findById() throws Exception{

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipes = Optional.of(recipe);

        when(recipeRepository.findById(id)).thenReturn(recipes);

        if (!recipes.isPresent()){
            throw new Exception("Error, no recipes");
        }

        assertEquals(recipes.get().getId(), id);


    }
    @Test
    public void saveRecipeCommandTest(){

        RecipeCommand commandEntry = new RecipeCommand();
        commandEntry.setId(1L);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(1L);
        notesCommand.setRecipeNotes("recipeNotes");
        commandEntry.setNotes(notesCommand);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(1L);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        RecipeCommand commandOutput = recipeService.saveRecipeCommand(commandEntry);

        assertEquals(1L, commandOutput.getId().longValue());





    }

    @Test
    public void testDeleteById() throws Exception {

        //given
        Long idToDelete = Long.valueOf(2L);

        //when
        recipeService.deleteById(idToDelete);

        //no 'when', since method has void return type

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

}