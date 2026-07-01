package com.IMS.InventoryManagementSystem.repositories;

import com.IMS.InventoryManagementSystem.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
