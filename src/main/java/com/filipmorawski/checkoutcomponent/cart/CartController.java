package com.filipmorawski.checkoutcomponent.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(description="Operations on shopping carts")
public class CartController {

	private CartService cartService;
		
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}


	@ApiOperation(value="View a list of opened shopping carts", response=List.class)
	@ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view carts"),
            @ApiResponse(code = 403, message = "Accessing carts you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "Carts you were trying to reach are not found")
    }
    )
	@GetMapping(path="/carts")
	public List<CartDTO> getAll () {
		return cartService.getAll();
	}
	
	@ApiOperation(value="Open new shopping cart", response=Cart.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cart succesfully opened"),
            @ApiResponse(code = 401, message = "You are not authorized to open cart"),
            @ApiResponse(code = 403, message = "Accessing the cart you were trying to open is forbidden"),
            @ApiResponse(code = 404, message = "The cart you were trying to reach is not found")
    }
    )
	@PostMapping(path="/carts")
	public CartDTO createCart() {
		return cartService.createCart();
	}
	
	@ApiOperation(value="Add product to existing shopping cart", response=Cart.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product succesfully added"),
            @ApiResponse(code = 401, message = "You are not authorized to add the product"),
            @ApiResponse(code = 403, message = "Accessing the cart you were trying to update is forbidden"),
            @ApiResponse(code = 404, message = "The cart you were trying to update is not found")
    }
    )
	@PostMapping(path="/carts/{cartId}/add/{quantity}/{productId}")
	public ResponseEntity<CartDTO> addProduct(
			@PathVariable(value="cartId") long cartId, 
			@PathVariable(value="productId") long productId, 
			@PathVariable(value="quantity") int quantity) {
		
		CartDTO cartDTO = cartService.addProduct(cartId,productId,quantity);
		if(cartDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cartDTO);
	}

	
	@ApiOperation(value="Close existing shopping cart and return final price", response=Cart.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cart succesfully closed"),
			@ApiResponse(code = 401, message = "You are not authorized to close the cart"),
            @ApiResponse(code = 403, message = "Accessing the cart you were trying to close is forbidden"),
            @ApiResponse(code = 404, message = "The cart you were trying to close is not found")
    }
    )
	@PostMapping(path="/carts/{cartId}/close")
	public ResponseEntity<CartDTO> closeCart(
			@PathVariable(value="cartId") long cartId) {
				
		CartDTO cart = cartService.closeCart(cartId);
		if(cart == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cart);
	}	
}
