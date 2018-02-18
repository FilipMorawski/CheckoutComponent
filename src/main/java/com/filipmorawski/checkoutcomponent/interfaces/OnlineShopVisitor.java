package com.filipmorawski.checkoutcomponent.interfaces;

import com.filipmorawski.checkoutcomponent.components.Cart;

public interface OnlineShopVisitor {

		public void visitShoppingCart(Cart cart);
}
