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


    @Override
    public CartDto getCart(int id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartMapper.toCartDto(cart);
    }

    @Override
    public CartDto AddProductToCart(int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < 1) {
            throw new RuntimeException("Product stock is empty");
        }

        Optional<CartItem> cartItemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        if (cartItemOpt.isPresent()) {
            return cartMapper.toCartDto(cart);
        }
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setUnitPrice(product.getPrice());
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);

        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();

        cart.setTotalPrice(totalPrice);
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


        double totalPrice = cartItemRepository.findAllByCart(cart)
                .stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();

        cart.setTotalPrice(totalPrice);
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
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setCartItems(new ArrayList<>());
        cart.setCustomer(customer);
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

    @Override
    public CartDto increaseQuantity(int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (cartItem.getQuantity() + 1 > product.getStock()) {
            throw new RuntimeException("Not enough stock");
        }
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);

        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();

        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);

        return cartMapper.toCartDto(cart);
    }

    @Override
    public CartDto decreaseQuantity(int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        } else {
            // Bir ürün varsa
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }


        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();

        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);

        return cartMapper.toCartDto(cart);
    }

}
