package com.example.bookstore.controller;

import com.example.bookstore.entity.UserEntity;
import com.example.bookstore.exception.FillAllFieldsException;
import com.example.bookstore.exception.UserAlreadyExistException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {


    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody UserEntity user, HttpServletRequest request) {
        try {
            userService.registration(user);
            LOGGER.info("Account: [201 Created] " + request.getRequestURL());
            return ResponseEntity.ok("Пользователь успешно создан!");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FillAllFieldsException e) {
            LOGGER.error("Account: [204 No Content] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Account: [500 Internal Server Error] " + request.getRequestURL());
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        try {
            userService.deleteUser(username, password);
            LOGGER.info("Account: [200 OK] " + request.getRequestURL());
            return ResponseEntity.ok("Пользователь удалён");
        } catch (UserNotFoundException e) {
            LOGGER.error("Account: [404 UserNotFound] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Product: [500 Internal Server Error] " + request.getRequestURL());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
