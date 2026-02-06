package com.gamzebolat.repository;

import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

}
