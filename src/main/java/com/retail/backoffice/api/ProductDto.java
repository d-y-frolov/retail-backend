package com.retail.backoffice.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	String id;
	String name;
	String groupId;
	String groupName;
	String unitId;
	String unitName;
	boolean isPieceUnit;
	Double tax;
	Double price;
	Double remainder;
	String manufacturer;
	String country;
}
