package com.gamzebolat.repository;

import com.gamzebolat.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    //activite=true olanları pageable kullanarak sayfalı şekilde getir
    Page<Product> findAllByActiveTrue(Pageable pageable);
}
