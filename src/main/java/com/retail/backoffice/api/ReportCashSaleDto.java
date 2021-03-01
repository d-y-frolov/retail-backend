package com.retail.backoffice.api;

import java.util.List;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportCashSaleDto {
	private String groupId;
	private String groupName;
	private List<DateSumDto> data ;
}
