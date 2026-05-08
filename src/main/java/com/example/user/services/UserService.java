package com.example.user.services;

import com.example.task.entity.Task;
import com.example.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private Map<Long, User> usersById = new HashMap<>();
    private List<User> userList = new ArrayList<>();
    private AtomicLong counterId = new AtomicLong(1);

    public User createUser(String name, String userName, String email, String password) {
        Long newId = counterId.getAndIncrement();
        User newUser = new User(newId, name, userName, email, password);

        validateUserName(userName);
        validateEmail(email);

        usersById.put(newId, newUser);
        userList.add(newUser);

        return newUser;
    }

    private void validateUserName(String userName) {

        boolean userNameTaken = userList.stream()
                .anyMatch(u -> u.getUserName().equalsIgnoreCase(userName));
        if (userNameTaken) {
            throw new IllegalArgumentException("userName '" + userName + "' já está em uso.");
        }
    }

    private void validateEmail(String email) {

        boolean emailTaken = userList.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (emailTaken) {
            throw new IllegalArgumentException("E-mail '" + email + "' já está cadastrado.");
        }
    }

    public List<User> listAll() {
        return new ArrayList<>(userList);
    }

    public Optional<User> searchById(Long id) {
        return Optional.ofNullable(usersById.get(id));
    }


}
