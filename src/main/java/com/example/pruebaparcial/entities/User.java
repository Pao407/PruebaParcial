package com.example.pruebaparcial.entities;

import java.sql.Date;
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
    private String identificador;
    private Double valor;
    private String nombreContratante;
    private String documentoContratante;
    private String nombreContratantista;
    private String documentoContratantista;
    private Date fechaInicial;
    private Date fechaFinal;
    
  
	
   @Override
   public int hashCode() {
       return Objects.hash(identificador, valor, nombreContratante, documentoContratante, nombreContratantista, documentoContratantista, fechaInicial, fechaFinal);
   }

   @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return Objects.equals(identificador, user.identificador) 
        && Objects.equals(valor, user.valor) 
        && Objects.equals(nombreContratante, user.nombreContratante) 
        && Objects.equals(documentoContratante, user.documentoContratante) 
        && Objects.equals(nombreContratantista, user.nombreContratantista) 
        && Objects.equals(documentoContratantista, user.documentoContratantista) 
        && Objects.equals(fechaInicial, user.fechaInicial) 
        && Objects.equals(fechaFinal, user.fechaFinal);
    }

}
