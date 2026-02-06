package com.gamzebolat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {
    private int quantity;
    private double unitPrice;

    @ManyToOne
    private Product product;

    @ManyToOne
    @JsonBackReference
    private Cart cart;

}
