package org.softuni.pastryShop.web;

import jakarta.validation.Valid;
import org.softuni.pastryShop.model.dto.CategoryDTO;
import org.softuni.pastryShop.model.dto.OrderDTO;
import org.softuni.pastryShop.model.dto.ProductDTO;
import org.softuni.pastryShop.model.dto.ProductDisplayDTO;
import org.softuni.pastryShop.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MeasureService measureService;
    private final CurrencyService currencyService;
    private final OrderService orderService;

    public ProductController(ProductService productService, CategoryService categoryService,
                             MeasureService measureService, CurrencyService currencyService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.measureService = measureService;
        this.currencyService = currencyService;
        this.orderService = orderService;
    }



    @GetMapping("/product")
    public ModelAndView getProducts(Model model,@PageableDefault(
                size=3,
                sort="price"
        ) Pageable pageable) {

        Page<ProductDisplayDTO> allProducts = productService.getAll(pageable);
        List<CategoryDTO> categoryDTOList = categoryService.getAll();
        model.addAttribute("products",allProducts);
        model.addAttribute("categories",categoryDTOList);

        return new ModelAndView("product");
    }

    @GetMapping("/addProduct")
    public ModelAndView addProduct(Model model) {
        if (!model.containsAttribute("productDTO")) {
            model.addAttribute("productDTO", ProductDTO.empty());

        }
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("measures", measureService.getAll());
        model.addAttribute("categories", categoryService.getAll());



        return new ModelAndView("addProduct");
    }


    @PostMapping("/addProduct")
    public ModelAndView addNewProduct(@ModelAttribute("productDTO") @Valid ProductDTO productDTO,
                                      BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("addProduct");
        }
        productService.addProduct(productDTO);
        return new ModelAndView("redirect:/index");
    }

    @GetMapping("/products/edit/{id}")
    public ModelAndView editProduct(@PathVariable String id, Model model) throws IOException {
        ProductDTO product = productService.getProductDTOById(Long.parseLong(id));

        if (!model.containsAttribute("productDTO")) {
            model.addAttribute("productDTO", product);
        }

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("measures",measureService.getAll());
        model.addAttribute("action", "/products/edit/{id}(id=" + id + ")");
        return new ModelAndView("editProduct");
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(
            @ModelAttribute("productDTO") @Valid ProductDTO productDTO,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return ("redirect:/product/edit/" + productDTO.getId());
        }
        productService.update(productDTO);

        return ("redirect:/product");
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable String id) throws IOException {

        ProductDTO categoryId = productService.getProductDTOById(Long.parseLong(id));
        Long productDTOId = categoryId.getId();
        productService.delete(Long.parseLong(id));

        return ("redirect:/product");
    }

    @GetMapping("/products/add-to-shopping-cart/{id}")
    public String addProductToShoppingCart(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        ProductDisplayDTO product = this.productService.getProductDisplayDTOById(Long.parseLong(id));
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);

        this.orderService.addProductToOrder(orderDTO, product);

        return ("redirect:/category/" + product.getCategoryId());
    }

}
