package com.bootcamp.java.product.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bootcamp.java.product.domain.ProductParameter;
import com.bootcamp.java.product.web.model.ProductParameterModel;

@Mapper(componentModel = "spring")
public interface ProductParameterMapper {
	
	ProductParameter modelToEntity(ProductParameterModel model);
	
	ProductParameterModel entityToModel(ProductParameter event);

	@Mapping(target = "id", ignore = true)
	//@Mapping(target = "idProduct", ignore = true)
	void update(@MappingTarget ProductParameter entity, ProductParameter updateEntity);
}
