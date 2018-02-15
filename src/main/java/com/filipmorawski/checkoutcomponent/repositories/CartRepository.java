package com.filipmorawski.checkoutcomponent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filipmorawski.checkoutcomponent.components.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
