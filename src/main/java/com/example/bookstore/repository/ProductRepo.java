package com.example.bookstore.repository;

import com.example.bookstore.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<ProductEntity, Long> {
    ProductEntity findByBookName (String bookName);
}
