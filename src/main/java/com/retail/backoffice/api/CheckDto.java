package com.retail.backoffice.api;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckDto {
	String id;
	CashDto cash;
	ZonedDateTime dateTime;
	Double sum;
	List<CheckDetailDto> details;
}
