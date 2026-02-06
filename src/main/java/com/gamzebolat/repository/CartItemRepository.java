package com.gamzebolat.repository;

import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.CartItem;
import com.gamzebolat.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findAllByCart(Cart cart);
}
