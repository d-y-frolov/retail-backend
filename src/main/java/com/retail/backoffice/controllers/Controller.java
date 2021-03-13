package com.retail.backoffice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.backoffice.api.ApiConstants;
import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.CheckDto;
import com.retail.backoffice.api.DateSumDto;
import com.retail.backoffice.api.DtoWithRetCode;
import com.retail.backoffice.api.ErrorResponseDto;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.ReportCashSaleDto;
import com.retail.backoffice.api.ResponseDto;
import com.retail.backoffice.api.UnitDto;
import com.retail.backoffice.service.interfaces.IRetail;
import com.retail.backoffice.service.interfaces.IRetail.ReturnCodes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class Controller {
	/***************************************************
	 * GROUPS
	 **************************************************/
	@Autowired
	IRetail service;

	@GetMapping(value = ApiConstants.GROUP_ENDPOINT)
	List<GroupDto> getGroups() {
		return service.getAllGroups();
	}

	@GetMapping(value = ApiConstants.GROUP_ID_ENDPOINT)
	ResponseEntity<?> getGroup(@PathVariable String id, HttpServletRequest request) {
		GroupDto groupDto = service.getGroup(id);
		if (groupDto == null) {
			HttpStatus returnHttpStatus = HttpStatus.CONFLICT;
			return ResponseEntity.status(returnHttpStatus).body(ErrorResponseDto.builder()
					.status(returnHttpStatus.value())
					.message(IRetail.ReturnCodes.GROUP_NOT_FOUND.toString().concat(" : ").concat(id))
					.error(returnHttpStatus.name())
					.path(request.getRequestURI())
					.build());
		}
		return ResponseEntity.ok(groupDto);
	}

	@PostMapping(value = ApiConstants.GROUP_ENDPOINT)
	ResponseEntity<?> addGroup(@RequestBody GroupDto groupDto, HttpServletRequest request) {
		IRetail.ReturnCodes returnCode = service.addGroup(groupDto); 
		if ( returnCode == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		HttpStatus returnHttpStatus = HttpStatus.BAD_REQUEST;
		if ( returnCode == IRetail.ReturnCodes.GROUP_ALREADY_EXISTS) {
			returnHttpStatus = HttpStatus.CONFLICT;
			return ResponseEntity.status(returnHttpStatus).body(
					ErrorResponseDto.builder()
					.status(returnHttpStatus.value())
					.error(returnHttpStatus.name())
					.message(String.format("%s : %s", 
							returnCode.toString(), groupDto.getId() ))
					.path(request.getRequestURI())
					.build()
					);
		}
		return ResponseEntity.status(returnHttpStatus).body(
				ErrorResponseDto.builder()
				.status(returnHttpStatus.value())
				.error(returnHttpStatus.name())
				.message(String.format("%s :%s", 
						returnCode.toString(), groupDto.toString() ))
				.path(request.getRequestURI())
				.build()
				);
	}

	/***************************************************
	 * PRODUCTS
	 **************************************************/
	@GetMapping(value = ApiConstants.PRODUCT_ENDPOINT)
	List<ProductDto> getProducts(@RequestParam(name="search", required = false) String searchString) {
		if (searchString==null) {
			return service.getAllProducts();
		}
		return service.getSearchedProducts(searchString);
	}

	@GetMapping(value = ApiConstants.PRODUCT_ID_ENDPOINT)
	ResponseEntity<?> getProduct(@PathVariable String id, HttpServletRequest request) {
		ProductDto product = service.getProduct(id);
		if (product==null) {
			HttpStatus returnStatus = HttpStatus.CONFLICT; 
			return ResponseEntity.status(returnStatus).body(
					ErrorResponseDto.builder()
					.status(returnStatus.value())
					.error(returnStatus.name())
					.message(IRetail.ReturnCodes.PRODUCT_ID_NOT_FOUND.toString().concat(" : ").concat(id))
					.path(request.getRequestURI())
					.build());
		}
		return ResponseEntity.ok(product);
	}

	@PostMapping(value = ApiConstants.PRODUCT_ENDPOINT)
	ResponseEntity<?> addProduct(@RequestBody ProductDto productDto, HttpServletRequest request) {
		IRetail.ReturnCodes returnCode = service.addProduct(productDto);
		if (returnCode == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}else if (returnCode == IRetail.ReturnCodes.INPUT_OBJECT_IS_NULL) {
			return ResponseEntity.badRequest().build();
		}
		HttpStatus returnStatus = HttpStatus.CONFLICT; 
		return ResponseEntity.status(returnStatus).body(
				ErrorResponseDto.builder()
				.status(returnStatus.value())
				.error(returnStatus.name())
				.message(returnCode.toString())
				.path(request.getRequestURI())
				.build());
	}

	@PutMapping(value = ApiConstants.PRODUCT_ENDPOINT)
	ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
		IRetail.ReturnCodes returnCode = service.updateProduct(productDto);
		if (returnCode == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		if (returnCode == IRetail.ReturnCodes.INPUT_OBJECT_IS_NULL) {
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<String>(returnCode.toString(), HttpStatus.CONFLICT);
	}

	@DeleteMapping(value = ApiConstants.PRODUCT_ID_ENDPOINT)
	ResponseEntity<?> removeProduct(@PathVariable String id) {
		IRetail.ReturnCodes returnCode = IRetail.ReturnCodes.OK;
		try{
			returnCode = service.removeProduct(id);
		}catch(Exception e) {
			returnCode = IRetail.ReturnCodes.CANNOT_BE_REMOVED_DEPENDENCIES_EXIST; 
		}
		if (returnCode == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		return new ResponseEntity<String>(returnCode.toString(), HttpStatus.CONFLICT);
	}

	/***************************************************
	 * UNITS
	 **************************************************/
	@GetMapping(value = ApiConstants.UNIT_ENDPOINT)
	List<UnitDto> getUnits() {
		return service.getAllUnits();
	}

	@GetMapping(value = ApiConstants.UNIT_ID_ENDPOINT)
	UnitDto getUnit(@PathVariable String id) {
		return service.getUnit(id);
	}

	@PostMapping(value = ApiConstants.UNIT_ENDPOINT)
	ResponseEntity<?> addUnit(@RequestBody UnitDto unitDto) {
		if (service.addUnit(unitDto) == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	/***************************************************
	 * CASHES
	 **************************************************/
	@GetMapping(value = ApiConstants.CASH_ENDPOINT)
	List<CashDto> getCashes() {
		return service.getAllCacheRegisters();
	}

	@GetMapping(value = ApiConstants.CASH_ID_ENDPOINT)
	CashDto getCacheRegister(@PathVariable int id) {
		return service.getCacheRegister(id);
	}

	@PostMapping(value = ApiConstants.CASH_ENDPOINT)
	ResponseEntity<?> addCacheRegister(@RequestBody CashDto cashDto) {
		if (service.addCacheRegister(cashDto) == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	/***************************************************
	 * CHECKS
	 **************************************************/
	@GetMapping(value = ApiConstants.CHECK_ENDPOINT)
	List<CheckDto> getChecks() {
		return service.getAllChecks();
	}

	@GetMapping(value = ApiConstants.CHECK_ID_ENDPOINT)
	ResponseEntity<?> getCheck(@PathVariable String id, HttpServletRequest request) {
		CheckDto checkDto = service.getCheck(id);
		if (checkDto==null) {
			HttpStatus returnStatus = HttpStatus.CONFLICT; 
			return ResponseEntity.status(returnStatus).body(
					ErrorResponseDto.builder()
					.status(returnStatus.value())
					.error(returnStatus.name())
					.message(ReturnCodes.CHECK_ID_NOT_FOUND.toString())
					.path(request.getRequestURI())
					.build());
		}
		return ResponseEntity.ok(checkDto); 
	}

	@PostMapping(value = ApiConstants.CHECK_ENDPOINT)
	ResponseEntity<?> addCheck(@RequestBody CheckDto checkDto) {
		log.debug("checkDto: {}", checkDto);
		DtoWithRetCode<String> dtoWithRetCode = service.addCheck(checkDto);
		if (dtoWithRetCode==null) {
			return ResponseEntity.badRequest().build();
		}
		IRetail.ReturnCodes result = dtoWithRetCode.getReturnCode();
		log.debug("Result add check: {}", result);
		if (result != IRetail.ReturnCodes.OK) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ResponseDto.builder().type(ApiConstants.TYPE_CHECK_ID)
						.payload(dtoWithRetCode.getDto()).build());
	}
	/***************************************************
	 * REPORTS
	 **************************************************/
	@GetMapping(value=ApiConstants.REPORT_GROUP_SALES_ENDPOINT)
	List<ReportCashSaleDto> getCashSalesReport(@RequestParam(name="from") String stringFrom, 
			@RequestParam (name="to")String stringTo){
		return service.getReportCashSale(stringFrom, stringTo);
	}
	@GetMapping(value=ApiConstants.REPORT_SALES_ENDPOINT)
	List<DateSumDto> getSalesReport(@RequestParam(name="from") String stringFrom, 
			@RequestParam (name="to")String stringTo){
		return service.getReportSalesSumDto(stringFrom, stringTo);
	}

}
