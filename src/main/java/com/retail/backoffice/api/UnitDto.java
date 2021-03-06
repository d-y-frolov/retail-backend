package com.retail.backoffice.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitDto {
	String id;
	@Builder.Default
	String name="";
	boolean pieceUnit;
}
