package com.filipmorawski.checkoutcomponent.test.unit;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Test;

import com.filipmorawski.checkoutcomponent.cart.CartDTO;
import com.filipmorawski.checkoutcomponent.cart.CartProduct;
import com.filipmorawski.checkoutcomponent.product.Product;

public class CartTest {

	@Test
	public void whenAddProduct_shouldReturnCartWithSpecificCost() {
		Product product = new Product(1,"Headphones", new BigDecimal(40), 3, new BigDecimal(70));
		CartDTO cart = new CartDTO();
		
		LinkedList<CartProduct> productsList = new LinkedList<CartProduct>();
		cart.setProductsList(productsList);
		
		int quantity = 2;
		
		cart.addProduct(new CartProduct(product,quantity));
		
		assertThat(cart.getTotalCost()).isEqualTo(product.getUnitCost().multiply(new BigDecimal(quantity)));
	}

}
