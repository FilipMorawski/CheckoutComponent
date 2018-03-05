package com.filipmorawski.checkoutcomponent.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.filipmorawski.checkoutcomponent.discount.PricingPolicy;
import com.filipmorawski.checkoutcomponent.product.Product;

import io.swagger.annotations.ApiModelProperty;

public class CartDTO {

	@ApiModelProperty(notes="Database generated cart ID")
	private long cartId;

	@ApiModelProperty(notes="List of products in cart")
	private List<CartProduct> productsList;

	@ApiModelProperty(notes="Total cost of all products stored in cart")
	private BigDecimal totalCost = new BigDecimal(0);
	
	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public List<CartProduct> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<CartProduct> productsList) {
		this.productsList = productsList;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	
	public void addProduct(Product product, int quantity, PricingPolicy policy) {
		CartProduct cartProduct = null;
		if (new DuplicationValidator().validate(productsList, product)) {
			cartProduct = new CartProduct(product, quantity);
			productsList.add(cartProduct);	
		} else {
			for (CartProduct cp: productsList) {
				if (cp.getProduct().equals(product)) {
					totalCost = totalCost.subtract(cp.getCartedPrice());
					cartProduct = cp;
					cartProduct.setQuantity(cp.getQuantity() + quantity);
				}
			}
		}
		cartProduct.setCartedPrice(policy.calculatePrice(product, cartProduct.getQuantity()));
		totalCost = totalCost.add(cartProduct.getCartedPrice());
	}
	
	public void addProduct(CartProduct cp, PricingPolicy policy) {
		addProduct(cp.getProduct(), cp.getQuantity(), policy);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof CartDTO)) {
			return false;
		}
		
		CartDTO cart = (CartDTO) obj;
		return cartId == cart.cartId &&
				Objects.equals(productsList, cart.productsList) &&
				Objects.equals(totalCost, cart.totalCost);		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cartId, productsList, totalCost);
	}

	@Override
	public String toString() {
		return "CartDTO [cartId=" + cartId + ", productsList=" + productsList + ", totalCost=" + totalCost + "]";
	}
}
