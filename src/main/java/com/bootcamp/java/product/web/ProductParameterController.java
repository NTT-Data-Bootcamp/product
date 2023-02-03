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

import com.bootcamp.java.product.domain.ProductParameter;
import com.bootcamp.java.product.service.ProductParameterService;
import com.bootcamp.java.product.web.mapper.ProductParameterMapper;
import com.bootcamp.java.product.web.model.ProductParameterModel;

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
@RequestMapping("/v1/productparameter")
public class ProductParameterController {
	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private ProductParameterService productParameterService;
	
	@Autowired
	private ProductParameterMapper productParameterMapper;
	
	
	@Operation(
            summary = "Get a list of products Parameter",
            description = "Get a list of products Parameter registered in the system",
            responses = {
            		@ApiResponse(responseCode = "200",
                    description = "The response for the product Parameter request")
            }
    )
	@GetMapping
	public Mono<ResponseEntity<Flux<ProductParameterModel>>> getAll(){
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok()
			.body(productParameterService.findAll()
					.map(productParameter -> productParameterMapper.entityToModel(productParameter))));
	}
	
	/*
	@Operation(summary = "Funcionalidad de consulta de un ProductParameter por ID")
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200", description = "product Parameter found succesully.",
				content = { @Content(mediaType = "application/json",
				schema = @Schema(implementation = ProductParameter.class)) }),
		@ApiResponse(responseCode = "400", description = "ID not valid.",
			content = @Content),
		@ApiResponse(responseCode = "404", description = "ProductParameter not found.",
			content = @Content)
	})
	*/
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ProductParameterModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<ProductParameter> response = productParameterService.findById(id);
		log.info("getById executed {}", response);
		return response
				.map(productParameter -> productParameterMapper.entityToModel(productParameter))
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
		 **/
	}
	
	@PostMapping
	public Mono<ResponseEntity<ProductParameterModel>> create(@Valid @RequestBody ProductParameterModel request){
		log.info("create executed {}", request);
		return productParameterService.create(productParameterMapper.modelToEntity(request))
				.map(productParameter -> productParameterMapper.entityToModel(productParameter))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
							port, "productParameter", c.getId())))
							.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<ProductParameterModel>> updateById(@PathVariable String id, @Valid @RequestBody ProductParameterModel request){
		log.info("updateById executed {}:{}", id, request);
		return productParameterService.update(id, productParameterMapper.modelToEntity(request))
				.map(productParameter -> productParameterMapper.entityToModel(productParameter))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
						port, "product", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		log.info("deleteById executed {}", id);
		return productParameterService.delete(id)
				.map( r -> ResponseEntity.ok().<Void>build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	
	@GetMapping("getByIdProductAndActive/{idProduct}")
	public Mono<ResponseEntity<Flux<ProductParameterModel>>> getByIdProductAndActive(@PathVariable String idProduct){
		log.info("getByIdProductTypeAndActive executed in Controller {}", idProduct);
		return Mono.just(ResponseEntity.ok()
				.body(productParameterService.findByIdProductAndActive(idProduct, true)
						.map(productParameter -> productParameterMapper.entityToModel(productParameter))));
		/*
		 Flux<Product> response = productService.findByIdProductTypeAndActive(idProductType, true);		
		 return response
				.map(product -> productMapper.entityToModel(product))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
		**/
		
	}
	
	@GetMapping("getByIdProductAndClientTypeAndIdClientProfileAndActive/{idProduct}/{clientType}/{idClientProfile}")
	public Mono<ResponseEntity<ProductParameterModel>> getByIdProductAndClientTypeAndIdClientProfileAndActive(@PathVariable String idProduct,
			@PathVariable String clientType,
			@PathVariable String idClientProfile){
		log.info("getByIdProductAndClientTypeAndIdClientProfileAndActive executed en Controller idProduct = {}", idProduct);
		Mono<ProductParameter> response = productParameterService.findByIdProductAndClientTypeAndIdClientProfileAndActive(idProduct, clientType, idClientProfile, true);
		log.info("getByIdProductAndClientTypeAndIdClientProfileAndActive executed en Controller response = {}", response);
		return response
				.map(productParameter -> productParameterMapper.entityToModel(productParameter))
				.map(ResponseEntity::ok);
				//.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	
	@GetMapping("getByIdProductAndClientTypeAndActiveWithoutClientProfile/{idProduct}/{clientType}")
	public Mono<ResponseEntity<ProductParameterModel>> getByIdProductAndClientTypeAndActiveWithoutClientProfile(@PathVariable String idProduct,
			@PathVariable String clientType){
		log.info("getByIdProductAndClientTypeAndIdClientProfileAndActive executed en Controller idProduct = {}", idProduct);
		Mono<ProductParameter> response = productParameterService.findByIdProductAndClientTypeAndIdClientProfileAndActive(idProduct, clientType, null, true);
		log.info("getByIdProductAndClientTypeAndIdClientProfileAndActive executed en Controller response = {}", response);
		return response
				.map(productParameter -> productParameterMapper.entityToModel(productParameter))
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
		 **/
	}

}
