package com.gamzebolat.Dto;

import com.gamzebolat.entity.CartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCart {
    private double totalPrice;
    private List<DtoCartItem> CartItems;
}
