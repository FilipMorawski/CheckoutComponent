package com.filipmorawski.checkoutcomponent.discount;

import com.filipmorawski.checkoutcomponent.cart.CartDTO;

public interface OnlineShopVisitor {

		public void visitShoppingCart(CartDTO cart);
}
