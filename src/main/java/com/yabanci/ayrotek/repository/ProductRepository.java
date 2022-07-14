package com.yabanci.ayrotek.repository;

import com.yabanci.ayrotek.model.Product;
import com.yabanci.ayrotek.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByTax_Id(long id);
}
