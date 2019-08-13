package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    final Recipe recipe = new Recipe();

    RecipeController recipeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.recipeController = new RecipeController(recipeService);
    }

    @Test
    public void getShowPage() {

        when(recipeService.findById(Long.valueOf("111"))).thenReturn(recipe);

        String returnView = recipeController.showById("111", model);
        assertEquals("recipe/show", returnView);
        verify(recipeService,times(1)).findById(anyLong());
        verify(model, times(1)).addAttribute(eq("recipe"), any(Recipe.class));

    }

    @Test
    public void testMockMVC() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(get("/recipe/111/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));

    }
}