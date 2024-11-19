package com.example.pruebaparcial.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.pruebaparcial.dto.ChangeUserInfoDTO;
import com.example.pruebaparcial.dto.UserDTO;
import com.example.pruebaparcial.dto.UserInfoDTO;
import com.example.pruebaparcial.services.UserService;
import com.example.pruebaparcial.exceptions.UserNotFoundException;
import com.example.pruebaparcial.exceptions.UserNotValidException;
import com.example.pruebaparcial.entities.User;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
    * Obtiene la información de un usuario
    * @param userId
    * @return UserInfoDTO con la información del usuario como JSON
    */

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long userId) {
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfoDTO);
    }

    /**
    * Crea un nuevo usuario
    * @param userDTO
    * @return ResponseEntity<Void> con estado 200
    */

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok().build();
    }



    

    /**
    * Elimina un usuario existente
    * @param userDTO
    * @return ResponseEntity<Void> con estado 200
    */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

  

}

