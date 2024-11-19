package com.example.pruebaparcial.services;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.pruebaparcial.dto.UserDTO;
import com.example.pruebaparcial.dto.ChangeUserInfoDTO;
import com.example.pruebaparcial.dto.UserInfoDTO;

import com.example.pruebaparcial.entities.User;

import com.example.pruebaparcial.repositories.UserRepository;
import com.example.pruebaparcial.exceptions.UserNotFoundException;
import com.example.pruebaparcial.exceptions.UserNotValidException;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Obtiene la información de un usuario
     * @param userId
     * @throws UserNotFoundException si no se encuentra el usuario
     * @return UserInfoDTO con la información del usuario
     */

    public UserInfoDTO getUserInfo(Long userId) {
        UserInfoDTO user = userRepository.findUserInfoDTOById(userId);

        if (user == null) {
            throw new UserNotFoundException("User information not found by id: " + userId);
        }

        return user;
    }

    /**
    * Crea un nuevo usuario después de verificar si todos los campos son válidos
    user.getEmail()
    * @param user
    * @throws UserNotValidException si el usuario no es válido con un mensaje explicando por qué
    */

    public void createUser(UserDTO user) {

        if (userRepository.existsByIdentificador(user.getIdentificador())) {
            throw new UserNotValidException("Usuario con el identificador " + user.getIdentificador() + " ya existe");
        }

        User newUser = modelMapper.map(user, User.class);
        userRepository.save(newUser);
    }

   

    /**
    * Actualiza un usuario existente después de verificar si todos los campos son válidos y el correo electrónico ya está en uso
    * @param userInfoDTO
    * @param userId
    * @throws UserNotValidException si el usuario no es válido con un mensaje que explica por qué
    * @throws UserNotFoundException si no se encuentra el usuario
    */

    public void updateUser(ChangeUserInfoDTO userInfoDTO, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado por id:" + userId);
        }
        if (!user.getNombreContratante().equals(userInfoDTO.getNombreContratante()) && userRepository.existsByName(userInfoDTO.getNombreContratante())) {
            throw new UserNotValidException("Usuario con correo electrónico " + userInfoDTO.getNombreContratante() + " ya existe");
        }
        
        user.setNombreContratantista(userInfoDTO.getNombreContratantista());
        user.setDocumentoContratantista(userInfoDTO.getDocumentoContratantista());
        user.setFechaInicial(new java.sql.Date(userInfoDTO.getFechaInicial().getTime()));
        user.setFechaFinal(new java.sql.Date(userInfoDTO.getFechaFinal().getTime()));
        user.setValor(userInfoDTO.getValor());
        user.setNombreContratante(userInfoDTO.getNombreContratante());
        user.setDocumentoContratante(userInfoDTO.getDocumentoContratante());


        userRepository.save(user);
    }

    /**
    * Comprueba si el usuario es válido verificando que todos los campos sean válidos
    * @param user
    * @throws UserNotValidException si el usuario no es válido con un mensaje que explica por qué
    * @return true si el usuario es válido
    */


    public boolean isValidUser(UserDTO user) {
        if (user.getIdentificador() == null || user.getIdentificador().isEmpty()) {
            throw new UserNotValidException("El identificador no puede estar vacío");
        }
        if (user.getValor() == null || user.getValor() < 0) {
            throw new UserNotValidException("El valor no puede ser negativo");
        }
        if (user.getNombreContratante() == null || user.getNombreContratante().isEmpty()) {
            throw new UserNotValidException("El nombre del contratante no puede estar vacío");
        }
        if (user.getDocumentoContratante() == null || user.getDocumentoContratante().isEmpty()) {
            throw new UserNotValidException("El documento del contratante no puede estar vacío");
        }
        if (user.getNombreContratantista() == null || user.getNombreContratantista().isEmpty()) {
            throw new UserNotValidException("El nombre del contratantista no puede estar vacío");
        }
        if (user.getDocumentoContratantista() == null || user.getDocumentoContratantista().isEmpty()) {
            throw new UserNotValidException("El documento del contratantista no puede estar vacío");
        }
        if (user.getFechaInicial() == null) {
            throw new UserNotValidException("La fecha inicial no puede estar vacía");
        }
        if (user.getFechaFinal() == null) {
            throw new UserNotValidException("La fecha final no puede estar vacía");
        }
        
        return true;
    }

    /**
     * Elimina un usuario existente
     * @param userId
     * @throws UserNotFoundException si no se encuentra el usuario
     */

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado por id:" + userId);
        }
        userRepository.deleteById(userId);
    }

    /**
     * Listar todos los usuarios
     * @return Iterable<User>
    */

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
}
