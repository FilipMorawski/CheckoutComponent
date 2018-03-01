package com.filipmorawski.checkoutcomponent.test.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.filipmorawski.checkoutcomponent.cart.Cart;
import com.filipmorawski.checkoutcomponent.cart.CartProduct;
import com.filipmorawski.checkoutcomponent.product.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AddProductIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void addProductTest() {
		ResponseEntity<Cart> responseEntity = restTemplate.postForEntity("/api/carts",null, Cart.class);
		Cart cart = responseEntity.getBody();
		
		Product[] productList = restTemplate.getForObject("/api/products", Product[].class);
		
		Product productToAdd = productList[0];
		int quantityToAdd = 3;
		
		String firstRequest = "/api/carts/" + cart.getCartId() +"/add/" + quantityToAdd + "/"  
				+ productToAdd.getProductId();
		responseEntity = restTemplate.postForEntity(firstRequest, null, Cart.class);
		cart = responseEntity.getBody();
		
		List<CartProduct> productsList = cart.getProductsList();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, productsList.size());
		assertEquals(productToAdd.getName(), productsList.get(0).getProduct().getName());
		assertEquals(productToAdd.getProductId(), productsList.get(0).getProduct().getProductId());
		assertEquals(quantityToAdd, productsList.get(0).getQuantity());	
	}
}
