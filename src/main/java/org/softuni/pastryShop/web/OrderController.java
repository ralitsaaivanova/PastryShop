package org.softuni.pastryShop.web;

import org.softuni.pastryShop.model.dto.OrderDTO;
import org.softuni.pastryShop.model.dto.ProductDisplayDTO;
import org.softuni.pastryShop.service.OrderService;
import org.softuni.pastryShop.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/accountShoppingCard")
    public ModelAndView viewShoppingCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);

        if (!model.containsAttribute("orderDTO")) {
            model.addAttribute("orderDTO", orderDTO);
        }

        model.addAttribute("products", this.orderService.getAllProductsForOrder(orderDTO.getId()));

        return new ModelAndView("account");
    }

    @GetMapping("shoppingCard-make-order")
    public ModelAndView makeOrder(@AuthenticationPrincipal UserDetails userDetails){
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);
        this.orderService.finalizeOrder(orderDTO);
        return new ModelAndView("successfulOrder");
    }

    @GetMapping("shopping-cart/remove-product/{id}")
    public String removeProduct(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails){
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);
        ProductDisplayDTO product = this.productService.getProductDisplayDTOById(Long.parseLong(id));
        this.orderService.removeProductFromOrder(orderDTO, product);
        return "redirect:/shopping-cart/open";
    }
}
