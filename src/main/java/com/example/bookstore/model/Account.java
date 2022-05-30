package com.example.bookstore.model;

import com.example.bookstore.entity.AccountEntity;
import com.example.bookstore.entity.ProductEntity;

import java.util.ArrayList;

public class Account {

    private Integer balance;
    private ArrayList<Product> products;

    public static Account toModel(AccountEntity entity) {
        Account model = new Account();

        model.setBalance(entity.getBalance());

        return model;
    }

    public Account() {
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
