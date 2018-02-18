package com.filipmorawski.checkoutcomponent.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filipmorawski.checkoutcomponent.components.Cart;
import com.filipmorawski.checkoutcomponent.components.Product;
import com.filipmorawski.checkoutcomponent.components.UnitsDiscountVisitor;
import com.filipmorawski.checkoutcomponent.repositories.CartRepository;
import com.filipmorawski.checkoutcomponent.repositories.ProductRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(description="Operations on shopping carts")
public class CartController {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@ApiOperation(value="View a list of opened shopping carts", response=List.class)
	@ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view carts"),
            @ApiResponse(code = 403, message = "Accessing carts you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Carts you were trying to reach are not found")
    }
    )
	@GetMapping(path="/carts")
	public List<Cart> getAll () {
		return cartRepository.findAll();
	}
	
	@ApiOperation(value="Open new shopping cart", response=Cart.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cart succesfully opened"),
            @ApiResponse(code = 401, message = "You are not authorized to open cart"),
            @ApiResponse(code = 403, message = "Accessing the cart you were trying to open is forbidden"),
            @ApiResponse(code = 404, message = "The cart you were trying to reach is not found")
    }
    )
	@PostMapping(path="/carts/create")
	public Cart createCart() {
		return cartRepository.save(new Cart());
	}
	
	@ApiOperation(value="Add product to existing shopping cart", response=Cart.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product succesfully added"),
            @ApiResponse(code = 401, message = "You are not authorized to add the product"),
            @ApiResponse(code = 403, message = "Accessing the cart you were trying to update is forbidden"),
            @ApiResponse(code = 404, message = "The cart you were trying to update is not found")
    }
    )
	@PostMapping(path="/carts/add/{quantity}/{productId}/{cartId}")
	public ResponseEntity<Cart> addProduct(
			@PathVariable(value="cartId") long cartId, 
			@PathVariable(value="productId") long productId, 
			@PathVariable(value="quantity") int quantity) {
		
		Cart cart = cartRepository.findOne(cartId);
		Product product = productRepository.findOne(productId);
		if(cart == null || product == null) {
			return ResponseEntity.notFound().build();
		}
		cart.addProduct(product, quantity);
		Cart updatedCart = cartRepository.save(cart);
		return ResponseEntity.ok(updatedCart);
	}

	
	@ApiOperation(value="Close existing shopping cart and return final price", response=Cart.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cart succesfully closed"),
			@ApiResponse(code = 401, message = "You are not authorized to close the cart"),
            @ApiResponse(code = 403, message = "Accessing the cart you were trying to close is forbidden"),
            @ApiResponse(code = 404, message = "The cart you were trying to close is not found")
    }
    )
	@PostMapping(path="/carts/close/{cartId}")
	public ResponseEntity<Cart> closeCart(
			@PathVariable(value="cartId") long cartId) {
				
		Cart cart = cartRepository.findOne(cartId);
		if(cart == null) {
			return ResponseEntity.notFound().build();
		}
		cartRepository.delete(cartId);
		new UnitsDiscountVisitor().visitShoppingCart(cart);
		return ResponseEntity.ok(cart);
	}	
}
