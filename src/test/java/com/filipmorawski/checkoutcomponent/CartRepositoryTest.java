package com.filipmorawski.checkoutcomponent;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.filipmorawski.checkoutcomponent.components.Cart;
import com.filipmorawski.checkoutcomponent.repositories.CartRepository;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class CartRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CartRepository cartRepository;
	
	private Cart cart;
	private Cart cart2;
	
	@Before
	public void setUp() {
		cart = new Cart();
		cart2 = new Cart();
		
		cart.setTotalCost(new BigDecimal(20));
		cart2.setTotalCost(new BigDecimal(10));
		
		entityManager.persist(cart);
		entityManager.flush();

		entityManager.persist(cart2);
		entityManager.flush();
	}
	
	@Test
	public void whenFindById() {

		//when
		Cart findedCart = cartRepository.findOne(cart2.getCartId());
		Cart findedCart2 = cartRepository.findOne(cart.getCartId());
		
		//then
		assertThat(findedCart.getTotalCost()).isEqualTo(cart2.getTotalCost());
		assertThat(findedCart2.getTotalCost()).isEqualTo(cart.getTotalCost());
	}

	@Test
	public void whenFindAll() {
		
		//when
		List<Cart> carts = cartRepository.findAll();
		
		//then
		assertThat(carts.size()).isEqualTo(2);
		assertThat(carts.get(0)).isEqualTo(cart);
		assertThat(carts.get(1)).isEqualTo(cart2);
	}

	@Test
	public void whenUpdate() {
		cart.setTotalCost(new BigDecimal(50));
		Cart updatedCart = cartRepository.save(cart);
		
		Cart cartInRepo = cartRepository.findOne(cart.getCartId());
		
		assertThat(cartInRepo.getTotalCost()).isEqualTo(updatedCart.getTotalCost());
	}
	
	@Test
	public void whenRemove() {
		cartRepository.delete(cart);
		
		Cart removedCart = cartRepository.findOne(cart.getCartId());		
		List<Cart> cartList = cartRepository.findAll();
		
		assertThat(removedCart).isEqualTo(null);
		assertThat(cartList.size()).isEqualTo(1);
		
	}
	
}
