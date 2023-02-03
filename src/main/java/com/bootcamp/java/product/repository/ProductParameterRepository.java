package com.bootcamp.java.product.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.product.domain.ProductParameter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductParameterRepository extends ReactiveMongoRepository<ProductParameter, String>{
	
	Flux<ProductParameter> findByIdProductAndActive(String idProduct, Boolean active);
	
	Flux<ProductParameter> findByClientTypeAndActive(String clientType, Boolean active);
	
	Flux<ProductParameter> findByIdClientProfileAndActive(String idClientProfile, Boolean active);
	
	Flux<ProductParameter> findByIdProductAndClientTypeAndActive(String idProduct, String clientType, Boolean active);
	
	Mono<ProductParameter> findByIdProductAndClientTypeAndIdClientProfileAndActive(String idProduct, String clientType, String idClientProfile, Boolean active);
	
	
}
