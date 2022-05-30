package com.example.bookstore.service;

import com.example.bookstore.entity.AccountEntity;
import com.example.bookstore.entity.AccountProductEntity;
import com.example.bookstore.entity.ProductEntity;
import com.example.bookstore.entity.UserEntity;
import com.example.bookstore.exception.FillAllFieldsException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.model.Account;
import com.example.bookstore.model.Product;
import com.example.bookstore.repository.AccountProductRepo;
import com.example.bookstore.repository.AccountRepo;
import com.example.bookstore.repository.ProductRepo;
import com.example.bookstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private AccountProductRepo accountProductRepo;

    public Account checkAccount(String username, String password) throws UserNotFoundException {

        if (userRepo.findByUsernameAndPassword(username, password) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        } else {

            UserEntity user = userRepo.findByUsernameAndPassword(username, password);
            AccountEntity account = accountRepo.findByUser(user);
            Account viewAccount = Account.toModel(account);

            ArrayList<Product> products = new ArrayList<Product>();
            Iterable<AccountProductEntity> accountsProducts = accountProductRepo.findAll();
            Iterator<AccountProductEntity> iter = accountsProducts.iterator();

            while (iter.hasNext()) {
                AccountProductEntity accountProduct = accountProductRepo.findById(iter.next().getId()).get();
                if (accountProduct.getAccountId() == user.getAccount().getId()) {
                    Product product = Product.toModel(productRepo.findById(accountProduct.getProductId()).get());
                    product.setAmount(accountProduct.getAmount());
                    products.add(product);
                }
            }

            viewAccount.setProducts(products);

            return viewAccount;

        }

    }

    public String topUpBalance(Long id, AccountEntity json) throws FillAllFieldsException, UserNotFoundException {
        if (json.getBalance() == null) {
            throw new FillAllFieldsException("Заполните все поля!");
        }
        else if (userRepo.findById(id).isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        else {
            UserEntity user = userRepo.findById(id).get();
            AccountEntity account = accountRepo.findByUser(user);

            account.setBalance(json.getBalance() + account.getBalance());
            accountRepo.save(account);

            String balance = json.getBalance().toString();

            return balance;
        }
    }

    }
