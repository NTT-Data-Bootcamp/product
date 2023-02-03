package com.bootcamp.java.product.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.java.product.domain.ProductType;
import com.bootcamp.java.product.repository.ProductTypeRepository;
import com.bootcamp.java.product.web.mapper.ProductTypeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductTypeService {

	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private ProductTypeMapper productTypeMapper;


	public Flux<ProductType> findAll(){
		log.debug("findAll executed in service");
		return productTypeRepository.findAll();
	}

	public Mono<ProductType> findById(String productTypeId){
		log.debug("findById executed in service {}", productTypeId);
		return productTypeRepository.findById(productTypeId);
	}

	public Mono<ProductType> create(ProductType productType){
		log.debug("create ProductType executed in service {}", productType);
		
		return productTypeRepository.save(productType);
		
		
	
		/*
		Mono<ProductType> pt =  productTypeRepository.findById(productType.getId());
		
		return pt
				//.flatMap(__ -> Mono.<ProductType>error(new Exception("Error, id of ProductType has exists")))
				.flatMap(foundUser-> null != foundUser ? Mono.error(new InvalidClientDocumentTypeException())
	                     : Mono.just(productType))
				.switchIfEmpty(productTypeRepository.save(productType));
				//.switchIfEmpty(Mono.defer(() -> productTypeRepository.save(productType)))
				//.cast(ProductType.class);
				//Mono.error(new InvalidClientDocumentTypeException()))
	
		*/
	}	

	public Mono<ProductType> update(String productTypeId, ProductType productType){
		log.debug("update ClientProfile executed in service {}:{}", productTypeId, productType);
		return productTypeRepository.findById(productTypeId)
		.flatMap(dbProductType -> {
			productTypeMapper.update(dbProductType, productType);
			return productTypeRepository.save(dbProductType);
		});
	}

	public Mono<ProductType> delete(String productTypeId){
		log.debug("delete ClientProfile executed in service {}", productTypeId);
		return productTypeRepository.findById(productTypeId)
		.flatMap(existingProductType -> productTypeRepository.delete(existingProductType)
		.then(Mono.just(existingProductType)));
	}

	
}
