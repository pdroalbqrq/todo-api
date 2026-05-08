package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.services.UserService;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> listAll() {
        return userService.listAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody UserRequest request) {
        return userService.createUser(request.getName(), request.getUserName(),
                request.getEmail(), request.getPassword());
    }

    public static class UserRequest {
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        private String name;

        @NotBlank(message = "Username é obrigatório")
        @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
                message = "Username só pode conter letras, números, pontos, hífens e underscores")
        private String userName;

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        @Size(max = 255, message = "Email muito longo")
        private String email;

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        private String password;

        public String getName() { return name; }
        public String getUserName() { return userName; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }
}
