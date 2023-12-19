package org.softuni.pastryShop.web;

import jakarta.validation.Valid;
import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.dto.ProductDisplayDTO;
import org.softuni.pastryShop.service.CategoryService;
import org.softuni.pastryShop.service.CurrencyService;
import org.softuni.pastryShop.service.MeasureService;
import org.softuni.pastryShop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final MeasureService measureService;
    private final CurrencyService currencyService;



    public CategoryController(CategoryService categoryService,
                              ProductService productService,
                              MeasureService measureService,
                              CurrencyService currencyService

                              ) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.measureService = measureService;
        this.currencyService = currencyService;
    }

    @GetMapping("addCategory")
    public ModelAndView addCategory(Model model) {
        if(!model.containsAttribute("categoryDTO")){
            model.addAttribute("categoryDTO",CategoryDTO.empty());
        }
        return new ModelAndView("addCategory");
    }

    @PostMapping("addCategory")
    public ModelAndView addCategory(@ModelAttribute("categoryDTO") @Valid CategoryDTO categoryDTO,
                                    Model model,
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("bad_credentials", "true");
            //TODO: add bad credentials to category Add html

            return new ModelAndView("redirect:/addCategory");
        }
        categoryService.addCategory(categoryDTO);

        return new ModelAndView("redirect:/index");
    }

    @GetMapping("category/{id}")
    public ModelAndView getCategory(@PathVariable String id, Model model,
                                    @PageableDefault(
                                            size=3,
                                            sort="price"
                                    )Pageable pageable){

        CategoryDTO categoryDTO = categoryService.getCategoryById(Long.parseLong(id));

//        model.addAttribute("categories", categoryService.getAll());
        Page<ProductDisplayDTO> allProductsByCategoryId= productService.getAllProductsByCategory(Long.parseLong(id),pageable);

        model.addAttribute("products",allProductsByCategoryId);
        model.addAttribute("currentCategory",categoryDTO);

        ModelAndView modelAndView = new ModelAndView("category");
        modelAndView.addObject("id",id);

        return modelAndView;
    }


    @GetMapping("/category/edit/{id}")
    public ModelAndView editProduct(@PathVariable String id, Model model) throws IOException {
        ProductDTO product = productService.getProductDTOById(Long.parseLong(id));

        if (!model.containsAttribute("productDTO")) {
            model.addAttribute("productDTO", product);
        }

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("measures",measureService.getAll());
        model.addAttribute("action", "/category/edit/{id}(id=" + id + ")");
        return new ModelAndView("addProduct");
    }

    @PostMapping("/category/edit/{id}")
    public String editProduct(
            @ModelAttribute("productsAddBindingModel") @Valid ProductDTO productDTO,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return ("redirect:/product/edit/" + productDTO.getId());
        }
        productService.update(productDTO);
        Long categoryId = productDTO.getCategoryId();

        return ("redirect:/category/" + categoryId);
    }

    @GetMapping("/category/delete/{id}")
    public String deleteProduct(@PathVariable String id) throws IOException {

        ProductDTO productDTO = productService.getProductDTOById(Long.parseLong(id));
        Long productDTOCategoryId = productDTO.getCategoryId();
        productService.delete(Long.parseLong(id));

        return ("redirect:/category/"+ productDTOCategoryId);
    }

}
