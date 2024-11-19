package com.example.pruebaparcial.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;

import com.example.pruebaparcial.PruebaparcialApplication;
import com.example.pruebaparcial.dto.UserDTO;
import com.example.pruebaparcial.services.UserService;
import com.example.pruebaparcial.entities.User;
import com.example.pruebaparcial.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
webEnvironment = SpringBootTest.WebEnvironment.MOCK,
classes =PruebaparcialApplication.class)
@AutoConfigureMockMvc

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)

public class TestUserController {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
   
    @BeforeEach
    @Transactional
    void setUp() {
        userRepository.deleteAllInBatch();
    }

    // Test the user creation endpoint
    @Test
	@DirtiesContext
    @Transactional
    @Description("Prueba la creación del usuario donde todos los campos sean válidos")
    void givenCorrectUser_whenCreateUser_thenUserCreated() throws Exception {
        // Arrange
        // Creates a json string
        String request = 
        """
        {
            "email": "johnDoe@gmail.com",
            "name": "John",
            "surname": "Doe",
            "phone": "123456789"
        }
        """;

        // Act
        // Realizar una solicitud al punto final y esperar un estado OK
        mvc.perform(MockMvcRequestBuilders.post("/user/create")
            .contentType("application/json")
            .content(request))
            .andExpect(MockMvcResultMatchers.status().isOk());

         // Assert
        // Check if the only user was created  
        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());   
        User user = userRepository.findByEmail("johnDoe@gmail.com");
        assertNotNull(user);
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("123456789", user.getPhone());
        assertEquals("johnDoe@gmail.com", user.getEmail());
    }
}
