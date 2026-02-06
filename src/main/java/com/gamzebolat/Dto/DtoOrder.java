package com.gamzebolat.Dto;

import com.gamzebolat.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoOrder {
    private double totalPrice;
    private List<DtoOrderItem> orderItems = new ArrayList<>();

}
