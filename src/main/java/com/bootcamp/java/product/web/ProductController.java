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

import com.bootcamp.java.product.domain.Product;
import com.bootcamp.java.product.service.ProductService;
import com.bootcamp.java.product.web.mapper.ProductMapper;
import com.bootcamp.java.product.web.model.ProductModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/product")
public class ProductController {
	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductMapper productMapper;
	
	
	@Operation(
            summary = "Get a list of products",
            description = "Get a list of products registered in the system",
            responses = {
            		@ApiResponse(responseCode = "200",
                    description = "The response for the product request")
            }
    )
	@GetMapping
	public Mono<ResponseEntity<Flux<ProductModel>>> getAll(){
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok()
			.body(productService.findAll()
					.map(product -> productMapper.entityToModel(product))));
	}
	
	
	@Operation(summary = "Funcionalidad de consulta de un producte por ID")
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200", description = "product found succesully.",
				content = { @Content(mediaType = "application/json",
				schema = @Schema(implementation = Product.class)) }),
		@ApiResponse(responseCode = "400", description = "ID not valid.",
			content = @Content),
		@ApiResponse(responseCode = "404", description = "Product not found.",
			content = @Content)
	})
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ProductModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<Product> response = productService.findById(id);
		return response
				.map(product -> productMapper.entityToModel(product))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
		
		//        .switchIfEmpty(Mono.error(new DataNotFoundException("The data you seek is not here."))); // NO FUNCIONA
		/*
		 return serverRequest.bodyToMono(RequestDTO.class)
                .map((request) -> searchLocations(request.searchFields, request.pageToken))
                .flatMap( t -> ServerResponse
                        .ok()
                        .body(t, ResponseDTO.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build())
                ;
		 * */
	}
	
	@PostMapping
	public Mono<ResponseEntity<ProductModel>> create(@Valid @RequestBody ProductModel request){
		log.info("create executed {}", request);
		return productService.create(productMapper.modelToEntity(request))
				.map(product -> productMapper.entityToModel(product))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
							port, "product", c.getId())))
							.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<ProductModel>> updateById(@PathVariable String id, @Valid @RequestBody ProductModel request){
		log.info("updateById executed {}:{}", id, request);
		return productService.update(id, productMapper.modelToEntity(request))
				.map(product -> productMapper.entityToModel(product))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
						port, "product", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		log.info("deleteById executed {}", id);
		return productService.delete(id)
				.map( r -> ResponseEntity.ok().<Void>build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping("getByIdProductTypeAndActive/{idProductType}")
	public Mono<ResponseEntity<Flux<ProductModel>>> getByIdProductTypeAndActive(@PathVariable String idProductType){
		log.info("getByIdProductTypeAndActive executed in Controller {}", idProductType);
		return Mono.just(ResponseEntity.ok()
				.body(productService.findByIdProductTypeAndActive(idProductType, true)
						.map(product -> productMapper.entityToModel(product))));
		/*
		 Flux<Product> response = productService.findByIdProductTypeAndActive(idProductType, true);		
		 return response
				.map(product -> productMapper.entityToModel(product))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
		*/
		
	}
}
