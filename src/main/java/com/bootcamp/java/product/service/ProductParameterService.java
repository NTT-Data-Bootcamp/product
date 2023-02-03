package com.bootcamp.java.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.java.product.domain.ProductParameter;
import com.bootcamp.java.product.repository.ProductParameterRepository;
import com.bootcamp.java.product.web.mapper.ProductParameterMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductParameterService {
	
	@Autowired
	private ProductParameterRepository productParameterRepository;
	
	@Autowired
	private ProductParameterMapper productParameterMapper;
	

	public Flux<ProductParameter> findAll(){
		log.debug("findAll executed");
		return productParameterRepository.findAll();
	}

	public Mono<ProductParameter> findById(String productParameterId){
		log.debug("findById executed {}", productParameterId);
		return productParameterRepository.findById(productParameterId);
	}

	public Mono<ProductParameter> create(ProductParameter productParameter){
		log.debug("create service executed {}", productParameter);
		
		return productParameterRepository.save(productParameter);
	}
	
	

	public Mono<ProductParameter> update(String productParameterId, ProductParameter productParameter){
		log.debug("update executed {}:{}", productParameterId, productParameter);
		return productParameterRepository.findById(productParameterId)
		.flatMap(dbProductParameter -> {
			productParameterMapper.update(dbProductParameter, productParameter);
			return productParameterRepository.save(dbProductParameter);
		});		
		
	}

	public Mono<ProductParameter> delete(String productParameterId){
		log.debug("delete executed {}", productParameterId);
		return productParameterRepository.findById(productParameterId)
		.flatMap(existingProductParameter -> productParameterRepository.delete(existingProductParameter)
		.then(Mono.just(existingProductParameter)));
		}

	
	public Flux<ProductParameter> findByIdProductAndActive(String productParameterId, Boolean active){
		log.debug("findByIdProductTypeAndActive executed in service {} estado {}", productParameterId, active);
		return productParameterRepository.findByIdProductAndActive(productParameterId, active);
		
	}
	
	public Mono<ProductParameter> findByIdProductAndClientTypeAndIdClientProfileAndActive(String idProduct, String clientType, String idClientProfile, Boolean active){
		log.debug("findByIdProductAndClientTypeAndIdClientProfileAndActive executed in service idProduct = {} , clientType = {} , idClientProfile = {} , estado = {}", idProduct, clientType, idClientProfile, active);
		return productParameterRepository.findByIdProductAndClientTypeAndIdClientProfileAndActive(idProduct, clientType, idClientProfile, active);
		
	}
	
}
