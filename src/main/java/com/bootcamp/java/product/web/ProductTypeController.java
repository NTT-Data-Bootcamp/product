package com.bootcamp.java.product.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.java.product.domain.ProductType;
import com.bootcamp.java.product.service.ProductTypeService;
import com.bootcamp.java.product.web.mapper.ProductTypeMapper;
import com.bootcamp.java.product.web.model.ProductTypeModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/producttype")
public class ProductTypeController {

	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private ProductTypeMapper productTypeMapper;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<ProductTypeModel>>> getAll(){
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok()
			.body(productTypeService.findAll()
					.map(productType -> productTypeMapper.entityToModel(productType))));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ProductTypeModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<ProductType> response = productTypeService.findById(id);
		return response
				.map(productType -> productTypeMapper.entityToModel(productType))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<ProductTypeModel>> create(@Valid @RequestBody ProductTypeModel request){
		log.info("create executed {}", request);
		return productTypeService.create(productTypeMapper.modelToEntity(request))
				.map(productType -> productTypeMapper.entityToModel(productType))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
							port, "productType", c.getId())))
							.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<ProductTypeModel>> updateById(@Valid @PathVariable String id, @Valid @RequestBody ProductTypeModel request){
		log.info("updateById executed {}:{}", id, request);
		return productTypeService.update(id, productTypeMapper.modelToEntity(request))
				.map(productType -> productTypeMapper.entityToModel(productType))
				.flatMap(c ->
				Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
						port, "productType", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		log.info("deleteById executed {}", id);
		return productTypeService.delete(id)
				.map( r -> ResponseEntity.ok().<Void>build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	

}
