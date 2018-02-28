package com.filipmorawski.checkoutcomponent.discount;

import com.filipmorawski.checkoutcomponent.cart.Cart;

public interface OnlineShopVisitor {

		public void visitShoppingCart(Cart cart);
}
