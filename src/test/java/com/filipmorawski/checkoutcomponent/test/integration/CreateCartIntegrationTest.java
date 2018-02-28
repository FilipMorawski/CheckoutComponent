package com.filipmorawski.checkoutcomponent;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.filipmorawski.checkoutcomponent.cart.Cart;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateCartIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void createCart() {
		ResponseEntity<Cart> responseEntity = restTemplate.postForEntity("/api/carts/create", null, Cart.class);
		Cart cart = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(null,cart.getProductsList());
		assertEquals(new BigDecimal(0),cart.getTotalCost());
	}
	
}
