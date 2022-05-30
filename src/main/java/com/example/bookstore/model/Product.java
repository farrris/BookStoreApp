package com.example.bookstore.model;

import com.example.bookstore.entity.ProductEntity;

public class Product {

    private String author;
    private String name;
    private Integer amount;

    public static Product toModel(ProductEntity entity) {
        Product model = new Product();

        model.setAuthor(entity.getBookAuthor());
        model.setName(entity.getBookName());

        return model;
    }

    public Product() {}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
