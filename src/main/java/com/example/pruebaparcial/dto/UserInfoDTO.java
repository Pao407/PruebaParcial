package com.example.pruebaparcial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDTO {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private int imageId;

    // Constructor que coincide con la consulta
    public UserInfoDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
