package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMesureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMesureRepository unitOfMesureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMesureRepository unitOfMesureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMesureRepository = unitOfMesureRepository;
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String getIndexPage(){

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMesureRepository.findByDescription("Teaspoon");

        System.out.println("Cat id is " + categoryOptional.get().getId());
        System.out.println("UOM id is " + unitOfMeasureOptional.get().getId());
        return "index";
    }
}
