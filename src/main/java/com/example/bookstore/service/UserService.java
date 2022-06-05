package com.example.bookstore.service;

import com.example.bookstore.entity.AccountEntity;
import com.example.bookstore.entity.UserEntity;
import com.example.bookstore.exception.FillAllFieldsException;
import com.example.bookstore.exception.UserAlreadyExistException;
import com.example.bookstore.exception.UserNotFoundException;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.AccountRepo;
import com.example.bookstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    public boolean registration(UserEntity user) throws UserAlreadyExistException, FillAllFieldsException {
        if(userRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        }
        else if (user.getUsername() == null || user.getPassword() == null) {
            throw new FillAllFieldsException("Заполните все поля");
        }
        else {
            userRepo.save(user);
            AccountEntity account = new AccountEntity(0, user);
            accountRepo.save(account);
            user.setAccount(account);
            return true;
        }
    }

    public User deleteUser(String username, String password) throws  UserNotFoundException {
        UserEntity user = userRepo.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден");
        } else {
            Long id = user.getId();
            userRepo.deleteById(id);
        }
        return null;
    }
}
