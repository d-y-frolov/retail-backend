package com.retail.backoffice.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckDetailDto {
	ProductDto productDto;
	double quantity;
	double price;
	double sum;
}
