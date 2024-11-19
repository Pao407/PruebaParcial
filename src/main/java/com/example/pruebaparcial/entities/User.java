package com.example.pruebaparcial.entities;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String surname;
    private String phone;
    private int imageId;
    
    //@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
   // private List<Property> properties;
   //ponerlo en abajo 
	
   @Override
   public int hashCode() {
       return Objects.hash(email,  name, phone, surname);
   }

   @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return Objects.equals(email, user.email) 
                && Objects.equals(name, user.name) 
                && Objects.equals(phone, user.phone) 
                && Objects.equals(surname, user.surname);
    }

}
