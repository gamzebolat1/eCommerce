package com.gamzebolat.Dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private int productId;
    private int quantity;
}
