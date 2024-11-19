package com.example.pruebaparcial.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.pruebaparcial.entities.User;
import com.example.pruebaparcial.dto.UserInfoDTO;

// Interfaz del repositorio de usuarios que extiende JpaRepository para proporcionar métodos CRUD
public interface UserRepository extends JpaRepository<User, Long> {

    // Método para encontrar un usuario por su identificador 
    User findByIdentificador(String identificador);

    // Método para verificar si un usuario existe por su identificador
    boolean existsByIdentificador(String identificador);

    // Consulta personalizada para obtener el identificador de un usuario por su ID
    @Query("SELECT u.identificador FROM User u WHERE u.id = :userId")
    String findIdentificadorById(@Param("userId") Long userId);

    // Consulta personalizada para obtener el ID de un usuario por su identificador
    @Query("SELECT u.id FROM User u WHERE u.identificador = :identificador")
    Long findIdByIdentificador(@Param("identificador") String identificador);

    // Consulta personalizada para obtener un UserInfoDTO por el ID del usuario
    @Query("SELECT new com.example.pruebaparcial.dto.UserInfoDTO(u.id, u.name, u.email) FROM User u WHERE u.id = :userId")
    UserInfoDTO findUserInfoDTOById(@Param("userId") Long userId);
}
