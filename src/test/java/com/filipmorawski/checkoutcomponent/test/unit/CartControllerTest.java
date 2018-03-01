package com.filipmorawski.checkoutcomponent.test.unit;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.filipmorawski.checkoutcomponent.cart.CartController;
import com.filipmorawski.checkoutcomponent.cart.CartDTO;
import com.filipmorawski.checkoutcomponent.cart.CartProduct;
import com.filipmorawski.checkoutcomponent.discount.UnitsDiscountVisitor;
import com.filipmorawski.checkoutcomponent.product.Product;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(value = CartController.class, secure = false)
public class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartController cartController;

	private CartDTO cart;

	private Product product;

	@Before
	public void setup() {
		cart = new CartDTO();
		cart.setCartId(1);

		product = new Product(1,"Headphones", new BigDecimal(40), 3, new BigDecimal(70));

		int quantity = 3;
		CartProduct cp = new CartProduct();
		cp.setId(1);
		cp.setProduct(product);
		cp.setQuantity(quantity);
		cp.setCartedPrice(new BigDecimal(120));

		LinkedList<CartProduct> productsList = new LinkedList<CartProduct>();
		productsList.add(cp);
		cart.setProductsList(productsList);
		cart.setTotalCost(new BigDecimal(120));
	}

	@Test
	public void whenCreateCart_shouldReturnCartWithId1() throws Exception {

		CartDTO createdCart = new CartDTO();
		createdCart.setCartId(1);
		createdCart.setTotalCost(new BigDecimal(15));

		when(cartController.createCart()).thenReturn(createdCart);

		mockMvc	.perform(post("/api/carts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cartId", is(1)))
				.andExpect(jsonPath("$.productsList", is(createdCart.getProductsList())))
				.andExpect(jsonPath("$.totalCost", is(15)));
	}

	@Test
	public void whenGetAllCarts_shouldReturnCartListOfSize2() throws Exception {

		int quantity = cart	.getProductsList()
							.get(0)
							.getQuantity();

		CartDTO secondCart = new CartDTO();
		secondCart.setCartId(2);

		LinkedList<CartDTO> cartList = new LinkedList<CartDTO>();
		cartList.add(cart);
		cartList.add(secondCart);

		when(cartController.getAll()).thenReturn(cartList);

		mockMvc	.perform(get("/api/carts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].cartId", is(1)))
				.andExpect(jsonPath("$[0].productsList[0].product.name", is(product.getName())))
				.andExpect(jsonPath("$[0].productsList[0].product.productId", is(1)))
				.andExpect(jsonPath("$[0].productsList[0].product.unitCost", is(40)))
				.andExpect(jsonPath("$[0].productsList[0].cartedPrice", is(120)))
				.andExpect(jsonPath("$[0].productsList[0].quantity", is(quantity)))
				.andExpect(jsonPath("$[0].totalCost", is(120)))
				.andExpect(jsonPath("$[1].cartId", is(2)))
				.andExpect(jsonPath("$[1].productsList", is(secondCart.getProductsList())))
				.andExpect(jsonPath("$[1].totalCost", is(0)));
	}

	@Test
	public void whenAddProduct_shouldReturnCartWithProduct() throws Exception {

		ResponseEntity<CartDTO> re = ResponseEntity.ok(cart);
		int quantity = cart	.getProductsList()
							.get(0)
							.getQuantity();

		when(cartController.addProduct(cart.getCartId(), product.getProductId(), quantity)).thenReturn(re);

		mockMvc	.perform(post("/api/carts/1/add/3/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cartId", is(1)))
				.andExpect(jsonPath("$.productsList[0].product.productId", is(1)))
				.andExpect(jsonPath("$.productsList[0].product.name", is(product.getName())))
				.andExpect(jsonPath("$.productsList[0].product.unitCost", is(40)))
				.andExpect(jsonPath("$.productsList[0].cartedPrice", is(120)))
				.andExpect(jsonPath("$.productsList[0].quantity", is(quantity)))
				.andExpect(jsonPath("$.totalCost", is(120)));
	}

	@Test
	public void whenCloseCart_shouldReturnClosedCart() throws Exception {

		UnitsDiscountVisitor visitor = new UnitsDiscountVisitor();
		visitor.visitShoppingCart(cart);
		int quantity = cart	.getProductsList()
							.get(0)
							.getQuantity();
		ResponseEntity<CartDTO> re = ResponseEntity.ok(cart);

		when(cartController.closeCart(cart.getCartId())).thenReturn(re);

		mockMvc	.perform(post("/api/carts/1/close").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cartId", is(1)))
				.andExpect(jsonPath("$.productsList[0].product.productId", is(1)))
				.andExpect(jsonPath("$.productsList[0].product.name", is(product.getName())))
				.andExpect(jsonPath("$.productsList[0].product.unitCost", is(40)))
				.andExpect(jsonPath("$.productsList[0].cartedPrice", is(120)))
				.andExpect(jsonPath("$.productsList[0].quantity", is(quantity)))
				.andExpect(jsonPath("$.totalCost", is(70)));

	}

}
