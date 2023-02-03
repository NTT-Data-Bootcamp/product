package com.bootcamp.java.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.java.product.repository.ProductRepository;

import com.bootcamp.java.product.web.mapper.ProductMapper;
import com.bootcamp.java.product.domain.Product;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductMapper productMapper;
	

	public Flux<Product> findAll(){
		log.debug("findAll executed");
		return productRepository.findAll();
	}

	public Mono<Product> findById(String productId){
		log.debug("findById executed {}", productId);
		return productRepository.findById(productId);
	}

	public Mono<Product> create(Product product){
		log.debug("create service executed {}", product);
		
		return productRepository.save(product);
	}
	
	

	public Mono<Product> update(String productId, Product product){
		log.debug("update executed {}:{}", productId, product);
		return productRepository.findById(productId)
		.flatMap(dbProduct -> {
			productMapper.update(dbProduct, product);
			return productRepository.save(dbProduct);
		});		
		
	}

	public Mono<Product> delete(String productId){
		log.debug("delete executed {}", productId);
		return productRepository.findById(productId)
		.flatMap(existingProduct -> productRepository.delete(existingProduct)
		.then(Mono.just(existingProduct)));
		}

	public Flux<Product> findByIdProductTypeAndActive(String idProductType, Boolean active){
		log.debug("findByIdProductTypeAndActive executed in service {} estado {}", idProductType, active);
		return productRepository.findByIdProductTypeAndActive(idProductType, active);
		
	}
} 
