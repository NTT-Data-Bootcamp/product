package com.bootcamp.java.product;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bootcamp.java.product.repository.ProductRepository;
import com.bootcamp.java.product.service.ProductService;
import com.bootcamp.java.product.web.mapper.ProductMapper;

@ExtendWith(MockitoExtension.class)
public class MockitoTestIT_V4_VerificarInteraccionesTest {


	@Mock
	ProductRepository productRepository;
	
	@Mock
	ProductMapper productMapper;
	
	@InjectMocks
	ProductService productService;
	
	
    @Test
    void findAll() throws Exception {
    	productService.findAll();
    	productService.findAll();
    	verify(productRepository, times(2)).findAll();
    }

}
