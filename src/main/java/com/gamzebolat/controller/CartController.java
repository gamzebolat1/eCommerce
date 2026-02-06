package com.gamzebolat.controller;

import com.gamzebolat.Dto.DtoCart;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.service.ICartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping(path = "/addProductToCart/{cartId}/{productId}")
    public DtoCart AddProductToCart(@PathVariable(name = "cartId") int cartId,
                                    @PathVariable(name = "productId") int productId){
        return cartService.AddProductToCart(cartId,productId);
    }
    @GetMapping(path = "/getCart/{id}")
    public DtoCart getCart(@PathVariable(name = "id") int Id){
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
}
