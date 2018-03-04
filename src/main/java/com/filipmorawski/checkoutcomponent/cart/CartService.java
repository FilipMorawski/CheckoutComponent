package com.filipmorawski.checkoutcomponent.cart;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipmorawski.checkoutcomponent.discount.PricingPolicy;
import com.filipmorawski.checkoutcomponent.product.Product;
import com.filipmorawski.checkoutcomponent.product.ProductRepository;


@Service
public class CartService {

	private ModelMapper mapper;
	
    private	CartRepository cartRepository;
	
	private ProductRepository productRepository;
	
	private PricingPolicy policy;
	
	@Autowired
	public CartService(ModelMapper mapper, CartRepository cartRepository, ProductRepository productRepository,
			PricingPolicy policy) {
		this.mapper = mapper;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.policy = policy;
	}

	public CartDTO createCart() {
		return mapper.map(cartRepository.save(new Cart()), CartDTO.class);
	}
	
	public List<CartDTO> getAll() {
		List<Cart> cartList = cartRepository.findAll();
		LinkedList<CartDTO> dtoList = new LinkedList<CartDTO>();
		Iterator<Cart> it = cartList.iterator();
		while (it.hasNext()) {
			dtoList.add(mapper.map(it.next(), CartDTO.class));
		}
		return dtoList;
	}
	
	public CartDTO addProduct(long cartId, long productId, int quantity) {
		CartDTO cartDTO = mapper.map(cartRepository.findOne(cartId), CartDTO.class);
		Product product = productRepository.findOne(productId);
		if(cartDTO == null || product == null) {
			return null;
		}
		cartDTO.addProduct(product, quantity, policy);
		cartRepository.save(mapper.map(cartDTO, Cart.class));
		return mapper.map(cartRepository.findOne(cartId), CartDTO.class);
	}
	
	public CartDTO closeCart(long cartId) {
		CartDTO cartDTO = mapper.map(cartRepository.findOne(cartId), CartDTO.class);
		cartRepository.delete(cartId);
		return cartDTO;
	}
}
