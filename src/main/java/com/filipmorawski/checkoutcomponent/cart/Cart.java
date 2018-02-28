package com.filipmorawski.checkoutcomponent.cart;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.filipmorawski.checkoutcomponent.discount.OnlineShopElement;
import com.filipmorawski.checkoutcomponent.discount.OnlineShopVisitor;
import com.filipmorawski.checkoutcomponent.product.Product;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Cart implements OnlineShopElement{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ApiModelProperty(notes="Database generated cart ID")
	private long cartId;

	@OneToMany(cascade=CascadeType.ALL)
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

	public void addProduct(Product product, int quantity) {
		CartProduct cp = new CartProduct(product, quantity);
		productsList.add(cp);
		totalCost = totalCost.add(cp.getCartedPrice());
	}
	public void addProduct(CartProduct cp) {
		productsList.add(cp);
		totalCost = totalCost.add(cp.getCartedPrice());
	}

	@Override
	public  void accept(OnlineShopVisitor visitor) {
		visitor.visitShoppingCart(this);
	}	
}