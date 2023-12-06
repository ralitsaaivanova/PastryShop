package org.softuni.pastryShop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @GetMapping("addCategory")
    public String addCategory() {
        return "addCategory";
    }

}
