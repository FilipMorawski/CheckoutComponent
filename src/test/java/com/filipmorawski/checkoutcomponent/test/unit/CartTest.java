package com.filipmorawski.checkoutcomponent;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Test;

import com.filipmorawski.checkoutcomponent.cart.Cart;
import com.filipmorawski.checkoutcomponent.cart.CartProduct;
import com.filipmorawski.checkoutcomponent.product.Product;

public class CartTest {

	@Test
	public void testAddProduct() {
		Product product = new Product();
		Cart cart = new Cart();
		
		product.setName("Headphones");
		product.setProductId(1L);
		product.setUnitCost(new BigDecimal(40));
		product.setDiscountUnits(3);
		product.setSpecialPrice(new BigDecimal(80));
		
		LinkedList<CartProduct> productsList = new LinkedList<CartProduct>();
		cart.setProductsList(productsList);
		
		int quantity = 2;
		
		cart.addProduct(new CartProduct(product,quantity));
		
		assertThat(cart.getTotalCost()).isEqualTo(product.getUnitCost().multiply(new BigDecimal(quantity)));
	}

}
