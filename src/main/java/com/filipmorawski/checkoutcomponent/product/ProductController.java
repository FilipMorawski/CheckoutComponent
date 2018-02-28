package com.filipmorawski.checkoutcomponent.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(description="Getting info about products in store")
public class ProductController {

	@Autowired
	ProductRepository productRepository;
	
	@ApiOperation(value="View a list of available products", response=List.class)
	@GetMapping(path="/products")
	public List<Product> getAll () {
		return productRepository.findAll();
	}
}
