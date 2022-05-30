package com.example.bookstore.repository;

import com.example.bookstore.entity.AccountEntity;
import com.example.bookstore.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepo extends CrudRepository<AccountEntity, Long> {
    AccountEntity findByUser(UserEntity user);
}
