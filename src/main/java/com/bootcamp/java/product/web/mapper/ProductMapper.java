package com.bootcamp.java.product.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bootcamp.java.product.domain.Product;
import com.bootcamp.java.product.web.model.ProductModel;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	Product modelToEntity(ProductModel model);

	ProductModel entityToModel(Product event);

	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget Product entity, Product updateEntity);
}
