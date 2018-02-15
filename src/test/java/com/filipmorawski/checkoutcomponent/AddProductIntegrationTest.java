package com.filipmorawski.checkoutcomponent;

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

import com.filipmorawski.checkoutcomponent.components.Cart;
import com.filipmorawski.checkoutcomponent.components.CartProduct;
import com.filipmorawski.checkoutcomponent.components.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddProductIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void addProduct() {
		ResponseEntity<Cart> responseEntity = restTemplate.postForEntity("/api/carts/create",null, Cart.class);
		Cart cart = responseEntity.getBody();
		
		Product[] productList = restTemplate.getForObject("/api/products", Product[].class);
		
		Product productToAdd = productList[0];
		int quantityToAdd = 3;
		
		String s1 = "/api/carts/add/" + cart.getCartId() + "/"  
				+ productToAdd.getProductId() 
				+ "/" + quantityToAdd;
		responseEntity = restTemplate.postForEntity(s1, null, Cart.class);
		cart = responseEntity.getBody();
		
		List<CartProduct> productsList = cart.getProductsList();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, productsList.size());
		assertEquals(productToAdd.getName(), productsList.get(0).getProduct().getName());
		assertEquals(productToAdd.getProductId(), productsList.get(0).getProduct().getProductId());
		assertEquals(quantityToAdd, productsList.get(0).getQuantity());


		
	}
}
