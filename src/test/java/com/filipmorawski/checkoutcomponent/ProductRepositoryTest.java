package com.filipmorawski.checkoutcomponent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.filipmorawski.checkoutcomponent.components.Product;
import com.filipmorawski.checkoutcomponent.repositories.ProductRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ProductRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ProductRepository productRepository;
	
	private Product product1;
	private Product product2;
	
	@Before
	public void setUp() {
		product1 = new Product("Headphones", new BigDecimal(40), 3, new BigDecimal(70));
		product2 = new Product("USBDrive", new BigDecimal(10), 2, new BigDecimal(15));

		entityManager.persist(product1);
		entityManager.flush();

		entityManager.persist(product2);
		entityManager.flush();
	}
	
	@Test
	public void whenFindById() {

		//when
		Product findedProduct1 = productRepository.findOne(product1.getProductId());
		Product findedProduct2 = productRepository.findOne(product2.getProductId());
		
		//then
		assertThat(findedProduct1.getName()).isEqualTo(product1.getName());
		assertThat(findedProduct2.getName()).isEqualTo(product2.getName());
		assertThat(findedProduct1.getUnitCost()).isEqualTo(product1.getUnitCost());
		assertThat(findedProduct2.getUnitCost()).isEqualTo(product2.getUnitCost());

	}

	@Test
	public void whenFindAll() {
		
		//when
		List<Product> products = productRepository.findAll();
		
		//then
		assertThat(products.size()).isGreaterThanOrEqualTo(2);
		assertThat(products.get(products.size()-2)).isEqualTo(product1);
		assertThat(products.get(products.size()-1)).isEqualTo(product2);
	}
	

}
