package com.filipmorawski.checkoutcomponent.test.unit;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.filipmorawski.checkoutcomponent.cart.CartDTO;
import com.filipmorawski.checkoutcomponent.cart.CartProduct;
import com.filipmorawski.checkoutcomponent.discount.UnitsDiscountVisitor;
import com.filipmorawski.checkoutcomponent.product.Product;

public class UnitsDiscountVisitorTest {
	
	private UnitsDiscountVisitor discountVisitor;
	private CartDTO cart;

	@Before
	public void setUp() throws Exception {
		cart = new CartDTO();
		Product product = new Product(1,"Headphones", new BigDecimal(40), 3, new BigDecimal(70));
		CartProduct cp = new CartProduct();
		cp.setProduct(product);
		cp.setQuantity(8);
		LinkedList<CartProduct> productsList = new LinkedList<CartProduct>();
		productsList.add(cp);
		cart.setProductsList(productsList);
		discountVisitor = new UnitsDiscountVisitor();
	}

	@Test
	public void whenVisit_shouldReturnCartWithCostAfterDiscount() {
		BigDecimal expected = new BigDecimal(-100);	
		discountVisitor.visitShoppingCart(cart);
		assertEquals(expected, cart.getTotalCost());
	}

}
