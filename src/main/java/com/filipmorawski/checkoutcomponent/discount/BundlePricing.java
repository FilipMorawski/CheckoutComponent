package com.filipmorawski.checkoutcomponent.discount;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.filipmorawski.checkoutcomponent.product.Product;

@Component
public class BundlePricing implements PricingPolicy {

	@Override
	public BigDecimal calculatePrice(Product product, int quantity) {
		
		BigDecimal bundles = new BigDecimal(quantity/product.getDiscountUnits());
		BigDecimal remainder =new BigDecimal(quantity%product.getDiscountUnits());
		BigDecimal specialPrice = product.getSpecialPrice();
		BigDecimal unitCost = product.getUnitCost();
		
		return specialPrice.multiply(bundles).add(unitCost.multiply(remainder));
	}

}
