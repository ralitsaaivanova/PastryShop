package org.softuni.pastryShop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductAddController {
    @GetMapping("addProduct")
    public String addProduct(){
        return "addProduct";
    }
}
