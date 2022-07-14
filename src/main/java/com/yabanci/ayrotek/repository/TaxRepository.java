package com.yabanci.ayrotek.repository;

import com.yabanci.ayrotek.model.ProductType;
import com.yabanci.ayrotek.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax,Long> {

    Tax findByProductType(ProductType productType);
}
