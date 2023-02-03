
package com.bootcamp.java.product.domain;


import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "product")
public class Product {
	@Id
	private String id;
	
	@NotNull
	@Indexed(unique = true)
	private String name;
	
	@NotNull
	private String idProductType;
		
	@NotNull
    private Boolean active;
}
