package com.example.bookstore.repository;

import com.example.bookstore.entity.AccountProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountProductRepo extends CrudRepository<AccountProductEntity, Long> {
    AccountProductEntity findByProductId (Long productId);
    AccountProductEntity findByAccountIdAndProductId(Long accountId, Long productId);
}
