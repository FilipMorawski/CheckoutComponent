package com.filipmorawski.checkoutcomponent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filipmorawski.checkoutcomponent.components.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
