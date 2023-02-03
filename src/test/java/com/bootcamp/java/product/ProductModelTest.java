package com.bootcamp.java.product;

//import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Assertions;

import com.bootcamp.java.product.domain.Product;
import com.bootcamp.java.product.web.mapper.ProductMapper;

@SpringBootTest(classes = { ProductMapper.class })
public class ProductModelTest {

	/*
	@Test
	void validateToString() throws Exception {
		// given
		Product client = Product.builder().id("112345523").name("CARLOS").lastName("JUAREZ SALAZAR").clientType("PERSONNEL").build();

		// when
		assertNotNull(client.toString());
	}

	@Test
	void validateHashCode() throws Exception {
		// given
		Product client = Product.builder().id("112345523").name("CARLOS").lastName("JUAREZ SALAZAR").clientType("PERSONNEL").build();

		// when
		assertNotNull(client.hashCode());
	}
	
	@Test
	void validateFieldClienType() throws Exception {
		// given
		Product client = Product.builder().id("112345523").name("CARLOS").lastName("JUAREZ SALAZAR").clientType("PERSONNEL").build();
		
		assertEquals(client.clientType, "BUSINESS");
		
	}
	*/
	

}
