package org.softuni.pastryShop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.softuni.pastryShop.model.dto.OrderDTO;
import org.softuni.pastryShop.model.dto.ProductDisplayDTO;
import org.softuni.pastryShop.model.entities.Order;
import org.softuni.pastryShop.model.entities.Product;
import org.softuni.pastryShop.model.entities.User;
import org.softuni.pastryShop.repository.OrderRepository;
import org.softuni.pastryShop.repository.ProductRepository;
import org.softuni.pastryShop.repository.UserRepository;
import org.softuni.pastryShop.service.OrderService;
import org.softuni.pastryShop.service.ProductService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class.getName());
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public void addOrder(OrderDTO orderDTO, UserDetails user) {

        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDTO, Order.class);
        User userEntity = userRepository.findByEmail(user.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with email " + user.getUsername() + " not found!"));

        order.setUser(userEntity);

        orderRepository.save(order);
    }

    @Override
    public void addProductToOrder(OrderDTO orderDTO, ProductDisplayDTO productDisplayDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDTO, Order.class);
//        User userEntity = this.userRepository.findByEmail(user.getUsername()).orElseThrow(() ->
//                new IllegalArgumentException("User with email " + user.getUsername() + " not found!"));
//
//        order.setUser(userEntity);

        Product product = this.productRepository.findById(productDisplayDTO.getId()).get();
        List<Product> productList = order.getProducts();
        productList.add(product);

        order.setProducts(productList);

        order.setPrice(this.calculatePrice(productList));
        order.setFinalPrice(this.calculateFinalPrice(order.getPrice(), order.getDiscount()));

        this.orderRepository.save(order);
    }

    @Override
    public void removeProductFromOrder(OrderDTO orderDTO, ProductDisplayDTO productDisplayDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDTO, Order.class);

        List<Product> productList = order.getProducts();
        productList.remove(productList.stream().filter(x -> x.getId() == productDisplayDTO.getId()).findFirst().get());

        order.setProducts(productList);

        order.setPrice(this.calculatePrice(productList));
        order.setFinalPrice(this.calculateFinalPrice(order.getPrice(), order.getDiscount()));

        this.orderRepository.save(order);
    }

    @Override
    public void removeAllProductFromOrder(OrderDTO orderDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDTO, Order.class);

        order.setProducts(new ArrayList<Product>());

        order.setPrice(0.0);
        order.setFinalPrice(0.0);

        this.orderRepository.save(order);
    }

    @Override
    public void finalizeOrder(OrderDTO orderDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDTO, Order.class);

        order.setFinalized(true);

        this.orderRepository.save(order);
    }

    @Override
    public OrderDTO getOrderDTOById(long id) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this.orderRepository.findById(id).get(), OrderDTO.class);
    }

    @Override
    public List<ProductDisplayDTO> getAllProductsForOrder(long id) {
        Order order = this.orderRepository.findById(id).get();

        List<ProductDisplayDTO> productDisplayDTOs = new ArrayList<>();

        for (Product product :
                order.getProducts()) {

            productDisplayDTOs.add(this.productService.mapProductToProductDisplayDTO(product));
        }

        return productDisplayDTOs;
    }

    @Override
    public OrderDTO getActiveOrderForUser(UserDetails userDetails) {
        OrderDTO orderDTO = null;
        User user = this.userRepository.findByEmail(userDetails.getUsername()).get();
        List<Order> orders = this.orderRepository.findByUser(user);
        ModelMapper modelMapper = new ModelMapper();
        if (orders != null && !orders.isEmpty()) {
            Optional<Order> orderToMap = orders.stream().filter(x -> !x.isFinalized()).findFirst();
            if (orderToMap.isPresent()){
                orderDTO = modelMapper.map(orderToMap.get(), OrderDTO.class);
            }
        }

        if (orderDTO == null) {
            Order order = new Order();
            order.setUser(user);
            order.setOrderNumber(this.getNextOrderNumber());
            this.orderRepository.save(order);
            orders = this.orderRepository.findByUser(user);
            Optional<Order> orderToMap = orders.stream().filter(x -> !x.isFinalized()).findFirst();
            if (orderToMap.isPresent()){
                orderDTO = modelMapper.map(orderToMap.get(), OrderDTO.class);
            }
        }
        return orderDTO;
    }

    private double calculateFinalPrice(double price, double discountPercent) {
        return price - ((discountPercent / 100) * price);
    }

    private double calculatePrice(List<Product> products) {
        double price = 0;

        for (Product product :
                products) {
            price += product.getPrice();
        }
        return price;
    }

    private String getNextOrderNumber() {
        List<Order> orders = this.orderRepository.findAll();
        if (!orders.isEmpty()) {
            return String.format("%09d", Long.parseLong(String.valueOf((orders.get(orders.size() - 1).getId() + 1))));
        }
        return "0000000001";
    }
    @Scheduled(fixedDelay = 86400000)
    public void deleteLeftShoppingCart() throws InterruptedException {
        LOGGER.info("Deleted shopping cart at "+
                LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        List<Order> orders = this.orderRepository.findAll().stream().filter(x -> !x.isFinalized()).toList();

        this.orderRepository.deleteAll(orders);
    }
}
