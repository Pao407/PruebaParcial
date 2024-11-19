package com.example.pruebaparcial.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pruebaparcial.entities.Image;



public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findById(int id);
    List<Image> findAllByIdIn(List<Integer> ids);
}
