package com.bootcamp.java.product.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.product.domain.ProductType;

import ch.qos.logback.core.net.server.Client;
import reactor.core.publisher.Mono;

@Repository
public interface ProductTypeRepository extends ReactiveMongoRepository<ProductType, String>{
	
	
}
