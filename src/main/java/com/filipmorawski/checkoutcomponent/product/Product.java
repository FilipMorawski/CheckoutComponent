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

import com.filipmorawski.checkoutcomponent.discount.OnlineShopElement;
import com.filipmorawski.checkoutcomponent.discount.OnlineShopVisitor;

@Entity
@Immutable
public class Product implements OnlineShopElement{

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
	public void accept(OnlineShopVisitor visitor) {}
}
