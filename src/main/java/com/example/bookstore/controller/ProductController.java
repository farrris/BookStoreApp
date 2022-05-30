package com.example.bookstore.controller;

import com.example.bookstore.entity.ProductEntity;
import com.example.bookstore.exception.*;
import com.example.bookstore.repository.ProductRepo;
import com.example.bookstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/market")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity getProducts(HttpServletRequest request) {
        try {
            Object response = productService.getProducts();
            LOGGER.info("Product: [200 OK] " + request.getRequestURL());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error("Product: [500 Internal Server Error] " + request.getRequestURL());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestParam String username, @RequestParam String password, @RequestBody ProductEntity product, HttpServletRequest request) {
        try {
            productService.addProduct(product, username, password);
            LOGGER.info("Product: [201 Created] " + request.getRequestURL());
            return ResponseEntity.ok("Товар добавлен в магазин");
        } catch (UserNotFoundException e) {
            LOGGER.error("Account: [404 UserNotFound] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoPermissionException e) {
            LOGGER.error("Account: [403 Forbidden]" + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FillAllFieldsException e) {
            LOGGER.error("Product: [204 NoContent] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("[500 Internal Server Error] " + request.getRequestURL());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/deal")
    public ResponseEntity deal(@RequestParam String username, @RequestParam String password, @RequestBody ProductEntity product, HttpServletRequest request) {
        try {
            productService.deal(product, username, password);
            LOGGER.info("AccountProduct: [201 Created] " + request.getRequestURL());
            return ResponseEntity.ok("Сделка прошла успешно");
        } catch (UserNotFoundException e) {
            LOGGER.error("Account: [404 UserNotFound] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FillAllFieldsException e) {
            LOGGER.error("AccountProduct: [204 NoContent] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughMoneyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotEnoughProductsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("[500 Internal Server Error] " + request.getRequestURL());
            return ResponseEntity.badRequest().body("Error");
        }
    }
}

