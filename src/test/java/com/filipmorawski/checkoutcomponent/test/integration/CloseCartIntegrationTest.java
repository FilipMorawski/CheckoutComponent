package com.filipmorawski.checkoutcomponent.test.integration;

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

import com.filipmorawski.checkoutcomponent.cart.CartDTO;
import com.filipmorawski.checkoutcomponent.discount.UnitsDiscountVisitor;
import com.filipmorawski.checkoutcomponent.product.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CloseCartIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void closeCartTest() {
		ResponseEntity<CartDTO> responseEntity = restTemplate.postForEntity("/api/carts",null, CartDTO.class);
		CartDTO cart = responseEntity.getBody();

		Product[] productList = restTemplate.getForObject("/api/products", Product[].class);
		
		Product productToAdd = productList[0];
		int quantityToAdd = 8;
		
		String firstRequest = "/api/carts/" + cart.getCartId() +"/add/" + quantityToAdd + "/"  
				+ productToAdd.getProductId();
		responseEntity = restTemplate.postForEntity(firstRequest, null, CartDTO.class);
		cart = responseEntity.getBody();
		
		UnitsDiscountVisitor visitor = new UnitsDiscountVisitor();
		visitor.visitShoppingCart(cart);
		BigDecimal costAfterDiscount = cart.getTotalCost();
		
		String secondRequest = "/api/carts/" + cart.getCartId() +"/close/";
		responseEntity = restTemplate.postForEntity(secondRequest,null, CartDTO.class);
		cart = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, cart.getProductsList().size());
		assertEquals(costAfterDiscount, cart.getTotalCost());
	}
	
}
