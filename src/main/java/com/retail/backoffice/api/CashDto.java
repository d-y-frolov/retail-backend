package com.retail.backoffice.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashDto {
	private int id;
	@Builder.Default
	private String name="";
	@Builder.Default
	private String info="";
	private int lastCheckNumber;
	@Builder.Default
	private String checkPrefix = "";
}
