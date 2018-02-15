package com.filipmorawski.checkoutcomponent.components;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ApiModelProperty(notes="Database generated product ID")
	private long productId;
	
	@NotBlank
	@ApiModelProperty(notes="Product name")
	private String name;
	
	@NotNull
	@ApiModelProperty(notes="Cost of one unit of product")
	private BigDecimal unitCost;


	@NotNull
	@ColumnDefault("0")
	@ApiModelProperty(notes="Number of units required to get special price discount")
	private int discountUnits;
	

	@NotNull
	@ColumnDefault("0")
	@ApiModelProperty(notes="Special price in specific amount of product units")
	private BigDecimal specialPrice;
	
	

	public Product() {}
	


	public Product(String name, BigDecimal unitCost, int discountUnits, BigDecimal specialPrice) {
		this.name = name;
		this.unitCost = unitCost;
		this.discountUnits = discountUnits;
		this.specialPrice = specialPrice;
	}



	public int getDiscountUnits() {
		return discountUnits;
	}

	public void setDiscountUnits(int discountUnits) {
		this.discountUnits = discountUnits;
	}

	public BigDecimal getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(BigDecimal specialPrice) {
		this.specialPrice = specialPrice;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	
}
