package com.filipmorawski.checkoutcomponent.test.unit;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.filipmorawski.checkoutcomponent.cart.CartDTO;
import com.filipmorawski.checkoutcomponent.cart.CartProduct;
import com.filipmorawski.checkoutcomponent.discount.BundlePricing;
import com.filipmorawski.checkoutcomponent.discount.PricingPolicy;
import com.filipmorawski.checkoutcomponent.product.Product;

public class BundlePricingTest {
	
	private CartDTO cart;
	private BigDecimal expected;
	private PricingPolicy policy;

	@Before
	public void setUp() throws Exception {
		policy = new BundlePricing();
		cart = new CartDTO();
		LinkedList<CartProduct> productsList = new LinkedList<CartProduct>();
		cart.setProductsList(productsList);
		Product product = new Product(1,"Headphones", new BigDecimal(40), 3, new BigDecimal(70));
		CartProduct cp = new CartProduct();
		int quantity = 8;
		cp.setProduct(product);
		cp.setQuantity(quantity);
		cart.addProduct(cp, policy);
		expected = policy.calculatePrice(product, quantity);	
	}

	@Test
	public void whenCalculate_shouldReturnCostAfterDiscount() {
		assertEquals(expected, cart.getTotalCost());
	}

}
