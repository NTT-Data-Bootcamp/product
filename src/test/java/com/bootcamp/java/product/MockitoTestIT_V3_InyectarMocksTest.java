package com.bootcamp.java.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bootcamp.java.product.repository.ProductRepository;
import com.bootcamp.java.product.service.ProductService;
import com.bootcamp.java.product.web.mapper.ProductMapper;

@ExtendWith(MockitoExtension.class)
public class MockitoTestIT_V3_InyectarMocksTest {

	@Mock
	ProductRepository productRepository;
	
	@Mock
	ProductMapper productMapper;
	
	@InjectMocks
	ProductService productService;
	
	
    @Test
    void testMock() throws Exception {
    	productService.findAll();
    }

}
