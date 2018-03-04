package com.filipmorawski.checkoutcomponent.discount;

import java.math.BigDecimal;

import com.filipmorawski.checkoutcomponent.product.Product;

public interface PricingPolicy {
	
	BigDecimal calculatePrice(Product product, int quantity);
	
}
