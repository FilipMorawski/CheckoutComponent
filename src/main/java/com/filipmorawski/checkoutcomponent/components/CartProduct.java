package com.filipmorawski.checkoutcomponent.components;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Cartedproducts")
public class CartProduct {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonIgnore
	@ApiModelProperty(notes="Database generated carted product ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	@ApiModelProperty(notes="Product inserted into cart")
	private Product product;
	
	@JsonProperty("quantity")
	@ApiModelProperty(notes="Quantity of product inserted into cart")
	private int quantity;
	
	@ApiModelProperty(notes="Price of inserted amount of this type of product")
	private BigDecimal cartedPrice;

	public CartProduct() {}

	public CartProduct(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
		this.cartedPrice = product.getUnitCost().multiply(new BigDecimal(quantity));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getCartedPrice() {
		return cartedPrice;
	}

	public void setCartedPrice(BigDecimal cartedPrice) {
		this.cartedPrice = cartedPrice;
	}	
}
