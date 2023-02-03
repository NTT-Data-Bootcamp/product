package com.bootcamp.java.product.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.product.domain.Product;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
	Flux<Product> findByIdProductTypeAndActive(String idProductType, Boolean active);
}
