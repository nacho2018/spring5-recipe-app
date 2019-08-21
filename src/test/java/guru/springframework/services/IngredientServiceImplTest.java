package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    IngredientToIngredientCommand ingredientToIngredientCommand;
    UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    @Mock
    RecipeRepository recipeRepository;
    IngredientService service;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(uomConverter);
        service = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
    }

    @Test
    public void testFindByRecipeIdAndIngredientId() throws Exception{

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        uom.setDescription("uom description");
        Ingredient ingredient = new Ingredient();
        ingredient.setId(111L);
        ingredient.setUom(uom);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.getIngredients().add(ingredient);

        Optional<Recipe> optional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optional);

        IngredientCommand ic = this.service.findByRecipeIdAndIngredientId(1L, 111L);

        assertEquals(new Long(111), ic.getId());
        assertTrue("uom description".equals(ic.getUnitOfMeasure().getDescription()));

    }
}
