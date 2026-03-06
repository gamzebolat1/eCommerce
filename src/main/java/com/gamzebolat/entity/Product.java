package com.gamzebolat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity
{
    private String productName;
    private Integer stock;
    private Double price;

    @Column(unique = true)
    private String productCode;

    private Boolean active;
}
