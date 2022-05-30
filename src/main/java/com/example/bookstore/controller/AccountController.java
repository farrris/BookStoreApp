package com.example.bookstore.controller;

import com.example.bookstore.entity.AccountEntity;
import com.example.bookstore.exception.FillAllFieldsException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.model.Account;
import com.example.bookstore.repository.AccountRepo;
import com.example.bookstore.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity checkAccount(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        try {
            Account response = accountService.checkAccount(username, password);
            LOGGER.info("Account: [200 OK] " + request.getRequestURL());
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            LOGGER.error("Account: [404 UserNotFound] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Account: [500 Internal Server Error] " + request.getRequestURL());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/balance")
    public ResponseEntity topUpBalance(@RequestParam Long id, @RequestBody AccountEntity json, HttpServletRequest request) {
        try {
            Object count = accountService.topUpBalance(id, json);
            LOGGER.info("Account: [200 OK] " + request.getRequestURL());
            return ResponseEntity.ok("Баланс пополнен на " + count.toString() + " рублей!");
        } catch (UserNotFoundException e) {
            LOGGER.error("Account: [404 UserNotFound] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FillAllFieldsException e) {
            LOGGER.error("Account: [204 NoContent] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Account: [500 Internal Server Error] " + request.getRequestURL());
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
