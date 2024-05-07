package com.gm.practicasya.repositories;


import com.gm.practicasya.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
