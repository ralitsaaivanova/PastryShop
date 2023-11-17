package org.softuni.pastryShop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/product")
    public String getProducts(){
        return "product";
    }

    @GetMapping("/cheesecakes")
    public String getCheesecakes(){
        return "cheesecakes";
    }

    @GetMapping("/cakeswithoutchocolate")
    public String getCakesWithoutChocolate(){
        return "cakeswithoutchocolate";
    }

    @GetMapping("/chocolatecakes")
    public String getChocolatecakes(){
        return "chocolatecakes";
    }

    @GetMapping("/cupcakes")
    public String getCupcakes(){
        return "cupcakes";
    }

    @GetMapping("/sweets")
    public String getSweets(){
        return "sweets";
    }

}
