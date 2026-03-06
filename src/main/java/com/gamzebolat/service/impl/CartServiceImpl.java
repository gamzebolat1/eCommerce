package com.gamzebolat.service.impl;

import com.gamzebolat.Dto.CartDto;
import com.gamzebolat.Dto.CartItemDto;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.CartItem;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.Product;
import com.gamzebolat.mapper.CartMapper;
import com.gamzebolat.repository.CartItemRepository;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.ProductRepository;
import com.gamzebolat.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    private double calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
    }


    @Override
    public CartDto getCart(int id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartMapper.toCartDto(cart);
    }

    @Override
    @Transactional
    public CartDto AddProductToCart(int cartId, int productId, int quantity) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        Optional<CartItem> cartItemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartItemOpt.isPresent()) {
            CartItem existingItem = cartItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = cartMapper.toCartItem(cart, product, quantity);
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        cart.setTotalPrice(calculateTotalPrice(cart));
        cartRepository.save(cart);

        return cartMapper.toCartDto(cart);
    }

    @Override
    public void removeProductFromCart(int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> cartItemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() - 1);

            if (cartItem.getQuantity() <= 0) {
                cart.getCartItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {
                cartItemRepository.save(cartItem);
            }
        }

        cart.setTotalPrice(calculateTotalPrice(cart));
        cartRepository.save(cart);
    }

    @Override
    public void emptyCart(int cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        for (CartItem item : new ArrayList<>(cart.getCartItems())) {
            cartItemRepository.delete(item);
        }
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    @Override
    public Cart createCartForCustomer(Customer customer) {
        Cart cart = cartMapper.fromCustomer(customer);
        customer.setCart(cart);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getValidatedCart(int cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        return cart;
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }


}
