package com.gamzebolat.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoOrderItem {
    private String productName;
    private double unitPrice;
    private double totalPrice;
}
