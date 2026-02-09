package com.gamzebolat.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCartItem {
    private int quantity;
    private double unitPrice;
    private String productName;
}
