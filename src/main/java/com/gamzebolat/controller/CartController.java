package com.gamzebolat.controller;

import com.gamzebolat.Dto.CartDto;
import com.gamzebolat.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final ICartService cartService;

    @PostMapping(path = "/addProductToCart/{cartId}/{productId}")
    public CartDto AddProductToCart(@PathVariable(name = "cartId") int cartId,
                                    @PathVariable(name = "productId") int productId){
        return cartService.AddProductToCart(cartId,productId);
    }
    @GetMapping(path = "/getCart/{id}")
    public CartDto getCart(@PathVariable(name = "id") int Id){
        return cartService.getCart(Id);
    }
    @DeleteMapping(path = "/removeProductFromCart/{cartId}/{productId}")
    public void removeProductFromCart(@PathVariable(name = "cartId") int cartId,
                                      @PathVariable(name = "productId") int productId){
        cartService.removeProductFromCart(cartId,productId);
    }
    @DeleteMapping(path = "/emptyCart/{cartId}")
    public void emptyCart(@PathVariable(name = "cartId") int cartId){
        cartService.emptyCart(cartId);
    }

    @PostMapping(path = "/{cartId}/{productId}/increase")
    public CartDto increaseQuantity(@PathVariable(name = "cartId") int cartId,
                                    @PathVariable(name = "productId") int productId){
        return cartService.increaseQuantity(cartId,productId);
    }
    @PostMapping(path = "/{cartId}/{productId}/decrease")
    public  CartDto decreaseQuantity(@PathVariable(name = "cartId") int cartId,
                                     @PathVariable(name = "productId") int productId){
        return  cartService.decreaseQuantity(cartId,productId);
    }


}
