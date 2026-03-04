package com.gamzebolat.controller;

import com.gamzebolat.Dto.AddToCartRequest;
import com.gamzebolat.Dto.CartDto;
import com.gamzebolat.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final ICartService cartService;

    @PostMapping("/addProductToCart/{cartId}")
    public CartDto addProductToCart(@PathVariable int cartId,
                                    @RequestBody AddToCartRequest request) {

        return cartService.AddProductToCart(
                cartId,
                request.getProductId(),
                request.getQuantity()
        );
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


}
