package guru.springframework.controllers;


import guru.springframework.domain.Recipe;

import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;



    public IndexController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(Model model){
        System.out.println("Getting index page");

        model.addAttribute("recipes", this.recipeService.getRecipes());
        return "index";
    }
}
