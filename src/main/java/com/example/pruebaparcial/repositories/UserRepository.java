package com.example.pruebaparcial.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.pruebaparcial.entities.User;
import com.example.pruebaparcial.dto.UserInfoDTO;

// Interfaz del repositorio de usuarios que extiende JpaRepository para proporcionar métodos CRUD
public interface UserRepository extends JpaRepository<User, Long> {

    // Método para encontrar un usuario por su email
    User findByEmail(String email);

    // Método para verificar si un usuario existe por su email
    boolean existsByEmail(String email);

    // Consulta personalizada para obtener el email de un usuario por su ID
    @Query("SELECT u.email FROM User u WHERE u.id = :userId")
    String findEmailById(@Param("userId") Long userId);

    // Consulta personalizada para obtener el ID de un usuario por su email
    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long findIdByEmail(@Param("email") String email);

    // Consulta personalizada para obtener un UserInfoDTO por el ID del usuario
    @Query("SELECT new com.example.pruebaparcial.dto.UserInfoDTO(u.id, u.name, u.email) FROM User u WHERE u.id = :userId")
    UserInfoDTO findUserInfoDTOById(@Param("userId") Long userId);
}
