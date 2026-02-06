package com.gamzebolat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JsonBackReference  // ✅ Eklendi - Child tarafı (JSON'da gösterilmez)
    private Order order;

    @ManyToOne
    private Product product;

    private String productName;
    private int quantity;
    private double unitPrice;
}