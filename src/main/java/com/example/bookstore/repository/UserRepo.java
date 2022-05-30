package com.example.bookstore.repository;

import com.example.bookstore.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername (String username);
    UserEntity findByUsernameAndPassword (String username, String password);
}
