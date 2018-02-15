package com.filipmorawski.checkoutcomponent.components;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipmorawski.checkoutcomponent.interfaces.Visitor;
import com.filipmorawski.checkoutcomponent.repositories.ProductRepository;

@Service
public class UnitsDiscountVisitor implements Visitor {
	
	@Override
	public BigDecimal visit(Cart cart) {
		
		Map<Long, Integer> productsCounter = mapProducts(cart);
		Set<Long> productsSet = productsCounter.keySet();
		BigDecimal totalCost = cart.getTotalCost();
		
		for(Long productId : productsSet) {
			Product product = getProduct(cart, productId);
			int discountUnits = product.getDiscountUnits();
			BigDecimal specialPrice = product.getSpecialPrice();
			BigDecimal normalPrice = product.getUnitCost().multiply(new BigDecimal(discountUnits));
			BigDecimal discount = normalPrice.subtract(specialPrice);
			double quantity = (double) productsCounter.get(productId);
			double discountCounter = quantity/discountUnits;
			if(discountCounter >= 1) {
				discountCounter = Math.floor(discountCounter);
				totalCost = totalCost.subtract(discount.multiply(new BigDecimal(discountCounter)));
				}
		}
		return totalCost;
	}

	private Product getProduct(Cart cart, Long productId) {

		List<CartProduct> productsList = cart.getProductsList();
		for (CartProduct cp : productsList) {
			if (cp.getProduct().getProductId() == productId) {
				return cp.getProduct();
			}
		}
		return null;
	}

	private Map<Long, Integer> mapProducts(Cart cart) {
		Map<Long, Integer> productsCounter = new HashMap<Long, Integer>();
		for (CartProduct cp : cart.getProductsList()) {
			long productId =  cp.getProduct().getProductId();
			if (productsCounter.containsKey(productId)) {
				int quantity1 = productsCounter.get(productId);
				int quantity2 = cp.getQuantity();
				productsCounter.put(productId, quantity1 + quantity2);
			} else {
				productsCounter.put(productId, cp.getQuantity());
			}
		}
		return productsCounter;
	}

}
