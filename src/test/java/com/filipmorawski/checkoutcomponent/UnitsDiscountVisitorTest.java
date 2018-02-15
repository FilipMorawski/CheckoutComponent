package com.filipmorawski.checkoutcomponent;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.filipmorawski.checkoutcomponent.components.Cart;
import com.filipmorawski.checkoutcomponent.components.CartProduct;
import com.filipmorawski.checkoutcomponent.components.Product;
import com.filipmorawski.checkoutcomponent.components.UnitsDiscountVisitor;

public class UnitsDiscountVisitorTest {
	
	private UnitsDiscountVisitor disVis;
	private Cart cart;

	@Before
	public void setUp() throws Exception {
		cart = new Cart();
		Product product = new Product();
		product.setName("Headphones");
		product.setProductId(1);
		product.setUnitCost(new BigDecimal(40));
		product.setDiscountUnits(3);
		product.setSpecialPrice(new BigDecimal(70));
		CartProduct cp = new CartProduct();
		cp.setProduct(product);
		cp.setQuantity(8);
		LinkedList<CartProduct> productsList = new LinkedList<CartProduct>();
		productsList.add(cp);
		cart.setProductsList(productsList);
		disVis = new UnitsDiscountVisitor();
	}

	@Test
	public void testVisit() {
		BigDecimal expected = new BigDecimal(-100);	
		assertEquals(expected, disVis.visit(cart));
	}

}
