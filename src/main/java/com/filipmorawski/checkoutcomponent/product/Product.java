package com.filipmorawski.checkoutcomponent.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

@Entity
@Immutable
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;

	@NotBlank
	private String name;

	@NotNull
	private BigDecimal unitCost;

	@NotNull
	@ColumnDefault("0")
	private int discountUnits;

	@NotNull
	@ColumnDefault("0")
	private BigDecimal specialPrice;

	public Product() {}

	public Product(String name, BigDecimal unitCost, int discountUnits, BigDecimal specialPrice) {
		this.name = name;
		this.unitCost = unitCost;
		this.discountUnits = discountUnits;
		this.specialPrice = specialPrice;		
	}
	
	public Product(long id,String name, BigDecimal unitCost, int discountUnits, BigDecimal specialPrice) {
		this(name,unitCost,discountUnits,specialPrice);
		this.productId = id;
	}


	public long getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public int getDiscountUnits() {
		return discountUnits;
	}

	public BigDecimal getSpecialPrice() {
		return specialPrice;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Product)) {
			return false;
		}
		Product product = (Product) obj;
		return productId == product.productId &&
				discountUnits == product.discountUnits &&
				Objects.equal(name, product.name) &&
				Objects.equal(specialPrice, product.specialPrice) &&
				Objects.equal(unitCost, product.unitCost);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(productId, name, specialPrice, unitCost, discountUnits);
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", unitCost=" + unitCost + ", discountUnits="
				+ discountUnits + ", specialPrice=" + specialPrice + "]";
	}
}
