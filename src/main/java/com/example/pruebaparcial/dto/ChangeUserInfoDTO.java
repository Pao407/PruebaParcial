package com.example.pruebaparcial.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ChangeUserInfoDTO {
    private String email;
    private String name;
    private String surname;
    private String phone;
    private boolean isHost;
    private boolean isRenter;
}
