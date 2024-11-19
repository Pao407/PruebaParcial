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
import com.example.pruebaparcial.entities.Image;
import com.example.pruebaparcial.entities.User;
import com.example.pruebaparcial.repositories.ImageRepository;
import com.example.pruebaparcial.repositories.UserRepository;
import com.example.pruebaparcial.exceptions.UserNotFoundException;
import com.example.pruebaparcial.exceptions.UserNotValidException;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.imageRepository = imageRepository;
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
    * @param user
    * @throws UserNotValidException si el usuario no es válido con un mensaje explicando por qué
    */

    public void createUser(UserDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserNotValidException("User with email " + user.getEmail() + " already exists");
        }

        User newUser = modelMapper.map(user, User.class);
        userRepository.save(newUser);
    }

    /**
    * Sube una foto para un usuario
    * @param photo
    * @param userId
    * @throws UserNotFoundException
    * @throws IOException
    */

    public void uploadPhoto(MultipartFile photo, Long userId) throws UserNotFoundException, IOException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("Usuario para subir fotos no encontrado por id: " + userId);
        }
        int imageId = user.getImageId();
        if (imageId != 0) {
            imageRepository.deleteById(imageId);
        }
        Image image = new Image();
        image.setImageData(photo.getBytes());
        image.setName(photo.getOriginalFilename());
        
        int newImageId = imageRepository.save(image).getId();
        user.setImageId(newImageId);
        userRepository.save(user);
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
        if (!user.getEmail().equals(userInfoDTO.getEmail()) && userRepository.existsByEmail(userInfoDTO.getEmail())) {
            throw new UserNotValidException("Usuario con correo electrónico " + userInfoDTO.getEmail() + " ya existe");
        }
        user.setEmail(userInfoDTO.getEmail());
        user.setName(userInfoDTO.getName());
        user.setSurname(userInfoDTO.getSurname());
        user.setPhone(userInfoDTO.getPhone());
        userRepository.save(user);
    }

    /**
    * Comprueba si el usuario es válido verificando que todos los campos sean válidos
    * @param user
    * @throws UserNotValidException si el usuario no es válido con un mensaje que explica por qué
    * @return true si el usuario es válido
    */


    public boolean isValidUser(UserDTO user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserNotValidException("El correo electrónico no puede estar vacío");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new UserNotValidException("El nombre no puede estar vacío");
        }
        if (user.getSurname() == null || user.getSurname().isEmpty()) {
            throw new UserNotValidException("El apellido no puede estar vacío");
        }
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            throw new UserNotValidException("El teléfono no puede estar vacío");
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
