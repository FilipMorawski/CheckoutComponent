package com.filipmorawski.checkoutcomponent.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long cartId;

	@OneToMany(cascade=CascadeType.ALL)
	private List<CartProduct> productsList;

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

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Cart ))  {
			return false;
		}
		Cart cart = (Cart) obj;
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
		return "Cart [cartId=" + cartId + ", productsList=" + productsList + ", totalCost=" + totalCost + "]";
	}
	
}
