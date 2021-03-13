package com.retail.backoffice.service.interfaces;

import java.util.List;

import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.CheckDto;
import com.retail.backoffice.api.DateSumDto;
import com.retail.backoffice.api.DtoWithRetCode;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.ReportCashSaleDto;
import com.retail.backoffice.api.UnitDto;

public interface IRetail {
	enum ReturnCodes {
		OK, INPUT_OBJECT_IS_NULL, CANNOT_BE_REMOVED_DEPENDENCIES_EXIST, 
		GROUP_NOT_FOUND, GROUP_ALREADY_EXISTS, 
		PRODUCT_ID_NOT_FOUND, PRODUCT_ID_ALREADY_EXISTS, PRODUCT_ID_EMPTY, 
		UNIT_NOT_FOUND, UNIT_ALREADY_EXISTS, 
		CASH_NOT_FOUND, CASH_ALREADY_EXISTS, 
		CHECK_ID_NOT_FOUND, CHECK_ID_ALREADY_EXISTS
	}

	/******************************************************
	 * PRODUCTS
	 ******************************************************/
	ProductDto getProduct(String id);

	List<ProductDto> getAllProducts();

	List<ProductDto> getSearchedProducts(String searchString);

	ReturnCodes addProduct(ProductDto productDto);
	
	ReturnCodes updateProduct(ProductDto productDto);

	ReturnCodes removeProduct(String id);

	/******************************************************
	 * GROUPS
	 ******************************************************/
	GroupDto getGroup(String id);

	List<GroupDto> getAllGroups();

	ReturnCodes addGroup(GroupDto groupDto);

	ReturnCodes updateGroup(GroupDto groupDto);

	ReturnCodes removeGroup(GroupDto groupDto);

	/******************************************************
	 * UNITS
	 ******************************************************/
	List<UnitDto> getAllUnits();

	UnitDto getUnit(String id);

	ReturnCodes addUnit(UnitDto unitDto);

	ReturnCodes updateUnit(UnitDto unitDto);

	ReturnCodes removeUnit(UnitDto unitDto);

	/******************************************************
	 * CASH REGISTERS
	 ******************************************************/
	List<CashDto> getAllCacheRegisters();

	CashDto getCacheRegister(int id);

	ReturnCodes addCacheRegister(CashDto cashDto);

	ReturnCodes updateCacheRegister(CashDto cashDto);
	
	ReturnCodes removeCacheRegister(CashDto cashDto);
	
	/******************************************************
	 * CHECKS
	 ******************************************************/
	List<CheckDto> getAllChecks();

	CheckDto getCheck(String id);

//	ReturnCodes addCheck(CheckDto checkDto);
	DtoWithRetCode<String> addCheck(CheckDto checkDto);

	ReturnCodes updateCheck(CheckDto checkDto);
	
	ReturnCodes removeCheck(CheckDto checkDto);

	/******************************************************
	 * REPORTS
	 ******************************************************/
	List<ReportCashSaleDto> getReportCashSale(String startDate, String endDate);
	List<DateSumDto> getReportSalesSumDto(String startDate, String endDate);
	
}
