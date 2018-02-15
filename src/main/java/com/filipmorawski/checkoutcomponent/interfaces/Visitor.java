package com.filipmorawski.checkoutcomponent.interfaces;

import java.math.BigDecimal;

import com.filipmorawski.checkoutcomponent.components.Cart;

public interface Visitor {

		public BigDecimal visit(Cart cart);
}
