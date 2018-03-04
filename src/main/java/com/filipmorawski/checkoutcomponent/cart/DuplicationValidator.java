package com.filipmorawski.checkoutcomponent.cart;

import java.util.Iterator;
import java.util.List;

import com.filipmorawski.checkoutcomponent.product.Product;

public class DuplicationValidator {

	public boolean validate (List<CartProduct> productsList, Product product) {
		Iterator<CartProduct> it = productsList.iterator();
		while (it.hasNext()) {
			CartProduct next = it.next();
			if (next.getProduct().equals(product)) {
				return false;
			}
		}
		return true;
	}

	
}
