package com.gamzebolat.service.impl;

import com.gamzebolat.Dto.DtoCart;
import com.gamzebolat.Dto.DtoCartItem;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.CartItem;
import com.gamzebolat.entity.Product;
import com.gamzebolat.repository.CartItemRepository;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.CustomerRepository;
import com.gamzebolat.repository.ProductRepository;
import com.gamzebolat.service.ICartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public DtoCart getCart(int id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        DtoCart dtoCart = new DtoCart();
        dtoCart.setTotalPrice(cart.getTotalPrice());

        List<DtoCartItem> dtoItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            DtoCartItem dtoItem = new DtoCartItem();
            dtoItem.setProductName(cartItem.getProduct().getProductName());
            dtoItem.setQuantity(cartItem.getQuantity());
            dtoItem.setUnitPrice(cartItem.getUnitPrice());
            dtoItems.add(dtoItem);
        }

        dtoCart.setCartItems(dtoItems);
        return dtoCart;
    }


    @Override
    public DtoCart AddProductToCart(int cartId,int productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() <= 0) {
            throw new RuntimeException("Product stock is empty");
        }

        Optional<CartItem> cartItemOpt = cartItemRepository.findByCartAndProduct(cart, product);

        double totalPrice = 0; // toplam fiyatı başlat

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);


        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setUnitPrice(product.getPrice());
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);


        }

        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.getQuantity() * item.getUnitPrice();
        }

        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);

        DtoCart dto = new DtoCart();
        dto.setTotalPrice(cart.getTotalPrice());

        List<DtoCartItem> dtoItems = new ArrayList<>();
        for (CartItem ci : cart.getCartItems()) {
            DtoCartItem dtoItem = new DtoCartItem();
            dtoItem.setProductName(ci.getProduct().getProductName());
            dtoItem.setQuantity(ci.getQuantity());
            dtoItem.setUnitPrice(ci.getUnitPrice());
            dtoItems.add(dtoItem);
        }

        dto.setCartItems(dtoItems);
        return dto;
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

        // totalPrice hesaplamak için veritabanından güncel cartItems al
        List<CartItem> items = cartItemRepository.findAllByCart(cart);
        double totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getQuantity() * item.getUnitPrice();
        }

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
}
