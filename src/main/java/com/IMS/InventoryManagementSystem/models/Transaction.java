package com.IMS.InventoryManagementSystem.models;

import com.IMS.InventoryManagementSystem.enums.TransactionStatus;
import com.IMS.InventoryManagementSystem.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Data
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer totalProducts;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String description;

    private LocalDateTime updateAt;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Suppliers suppliers;

    @Override
    public String toString() {
        return "Transaction{" +
                "createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", transactionType=" + transactionType +
                ", totalPrice=" + totalPrice +
                ", totalProducts=" + totalProducts +
                ", id=" + id +
                '}';
    }
}
