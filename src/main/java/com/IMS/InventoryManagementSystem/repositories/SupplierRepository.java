package com.IMS.InventoryManagementSystem.repositories;

import com.IMS.InventoryManagementSystem.models.Product;
import com.IMS.InventoryManagementSystem.models.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Suppliers, Long> {
}
