package com.example.bookstore.service;

import com.example.bookstore.entity.AccountEntity;
import com.example.bookstore.entity.AccountProductEntity;
import com.example.bookstore.entity.ProductEntity;
import com.example.bookstore.entity.UserEntity;
import com.example.bookstore.exception.*;
import com.example.bookstore.repository.AccountProductRepo;
import com.example.bookstore.repository.AccountRepo;
import com.example.bookstore.repository.ProductRepo;
import com.example.bookstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountProductRepo accountProductRepo;

    public Object getProducts() {
        Iterable<ProductEntity> products = productRepo.findAll();

        return products;
    }

    public String addProduct(ProductEntity JsonProduct, String username, String password) throws FillAllFieldsException, NoPermissionException, UserNotFoundException {

        ProductEntity product = productRepo.findByBookName(JsonProduct.getBookName());
        UserEntity user = userRepo.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        else if (user.getIs_admin() == false) {
            throw new NoPermissionException("Недостаточно прав");
        }
        else if (JsonProduct.getBookName() == null || JsonProduct.getBookAuthor() == null || JsonProduct.getAmount() == null || JsonProduct.getPrice() == null) {
            throw new FillAllFieldsException("Заполните все поля");
        } else if (product != null) {
            Integer amount = JsonProduct.getAmount() + product.getAmount();
            product.setAmount(amount);
            productRepo.save(product);
        } else {
            productRepo.save(JsonProduct);
        }
        return "Successfully";
    }

    public String deal(ProductEntity JsonProduct, String username, String password) throws NotEnoughMoneyException, NotEnoughProductsException, ProductNotFoundException, UserNotFoundException, FillAllFieldsException {

        ProductEntity product = productRepo.findById(JsonProduct.getId()).get();
        UserEntity user = userRepo.findByUsernameAndPassword(username, password);

        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден");
        } else if (JsonProduct.getId() == null || JsonProduct.getAmount() == null) {
            throw new FillAllFieldsException("Заполните все поля");
        } else if (productRepo.findById(JsonProduct.getId()).isEmpty()) {
            throw new ProductNotFoundException("Товар не найден");
        } else if (product.getAmount() < JsonProduct.getAmount()) {
            throw new NotEnoughProductsException("Недостаточно товара в наличии");
        } else if (user.getAccount().getBalance() < product.getPrice() * JsonProduct.getAmount()) {
            throw new NotEnoughMoneyException("Недостаточно денег на балансе");
        } else {

            Integer balance = user.getAccount().getBalance();
            Integer price = product.getPrice() * JsonProduct.getAmount();

            Long accountId = user.getAccount().getId();
            Long productId = product.getId();

            if (accountProductRepo.findByAccountIdAndProductId(accountId, productId) != null) {
                AccountProductEntity accountProductAlready = accountProductRepo.findByAccountIdAndProductId(accountId, productId);
                accountProductAlready.setAmount(accountProductAlready.getAmount() + JsonProduct.getAmount());
            } else {
                AccountProductEntity accountProduct = new AccountProductEntity(accountId, productId, JsonProduct.getAmount());
                accountProductRepo.save(accountProduct);
            }

            AccountEntity account = user.getAccount();
            account.setBalance(balance - price);
            accountRepo.save(account);

            product.setAmount(product.getAmount() - JsonProduct.getAmount());
            if (product.getAmount() == 0) {
                productRepo.delete(product);
            } else {
                productRepo.save(product);
            }

            return "Successfully";
        }
    }
}

