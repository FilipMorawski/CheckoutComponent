package com.filipmorawski.checkoutcomponent;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.filipmorawski.checkoutcomponent.components.Product;
import com.filipmorawski.checkoutcomponent.controllers.ProductController;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductController productController;

	@Test
	public void getAllTest() throws Exception{
		Product product = new Product("Headphones", new BigDecimal(40), 3, new BigDecimal(70));
		Product product2 = new Product("USBDrive", new BigDecimal(10), 2, new BigDecimal(15));
		
		product.setProductId(1);
		product2.setProductId(2);
		
		LinkedList<Product> productsRepo = new LinkedList<Product>();
		productsRepo.add(product);
		productsRepo.add(product2);
		
		when(productController.getAll()).thenReturn(productsRepo);

		mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].productId", is(1)))
		.andExpect(jsonPath("$[0].name", is(product.getName())))
		.andExpect(jsonPath("$[0].unitCost", is(40)))
		.andExpect(jsonPath("$[0].discountUnits", is(product.getDiscountUnits())))
		.andExpect(jsonPath("$[0].specialPrice", is(70)))
		.andExpect(jsonPath("$[1].productId", is(2)))
		.andExpect(jsonPath("$[1].name", is(product2.getName())))
		.andExpect(jsonPath("$[1].unitCost", is(10)))
		.andExpect(jsonPath("$[1].discountUnits", is(product2.getDiscountUnits())))
		.andExpect(jsonPath("$[1].specialPrice", is(15)));
	}
	
}
