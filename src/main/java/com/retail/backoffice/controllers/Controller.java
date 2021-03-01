package com.retail.backoffice.controllers;

import java.time.LocalDate;
import java.util.List;

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

import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.CheckDto;
import com.retail.backoffice.api.DateSumDto;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.ReportCashSaleDto;
import com.retail.backoffice.api.UnitDto;
import com.retail.backoffice.service.IRetail;

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

	@GetMapping(value = "/group")
	List<GroupDto> getGroups() {
		return service.getAllGroups();
	}

	@GetMapping(value = "/group/{id}")
	GroupDto getGroup(@PathVariable String id) {
		return service.getGroup(id);
	}

	@PostMapping(value = "/group")
	ResponseEntity<?> addGroup(@RequestBody GroupDto groupDto) {
		if (service.addGroup(groupDto) == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	/***************************************************
	 * PRODUCTS
	 **************************************************/
	@GetMapping(value = "/product")
	List<ProductDto> getProducts(@RequestParam(name="search", required = false) String searchString) {
		if (searchString==null) {
			return service.getAllProducts();
		}
		return service.getSearchedProducts(searchString);
	}

	@GetMapping(value = "/product/{id}")
	ProductDto getProduct(@PathVariable String id) {
		return service.getProduct(id);
	}

	@PostMapping(value = "/product")
	ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
		IRetail.ReturnCodes returnCode = service.addProduct(productDto);
		if (returnCode == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}else if (returnCode == IRetail.ReturnCodes.INPUT_OBJECT_IS_NULL) {
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<String>(returnCode.toString(), HttpStatus.CONFLICT);
	}

	@PutMapping(value = "/product")
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

	@DeleteMapping(value = "/product/{id}")
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
	@GetMapping(value = "/unit")
	List<UnitDto> getUnits() {
		return service.getAllUnits();
	}

	@GetMapping(value = "/unit/{id}")
	UnitDto getUnit(@PathVariable String id) {
		return service.getUnit(id);
	}

	@PostMapping(value = "/unit")
	ResponseEntity<?> addUnit(@RequestBody UnitDto unitDto) {
		if (service.addUnit(unitDto) == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	/***************************************************
	 * CASHES
	 **************************************************/
	@GetMapping(value = "/cash-register")
	List<CashDto> getCashes() {
		return service.getAllCacheRegisters();
	}

	@GetMapping(value = "/cash-register/{id}")
	CashDto getCacheRegister(@PathVariable int id) {
		return service.getCacheRegister(id);
	}

	@PostMapping(value = "/cash-register")
	ResponseEntity<?> addCacheRegister(@RequestBody CashDto cashDto) {
		if (service.addCacheRegister(cashDto) == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	/***************************************************
	 * CHECKS
	 **************************************************/
	@GetMapping(value = "/check")
	List<CheckDto> getChecks() {
		return service.getAllChecks();
	}

	@GetMapping(value = "/check/{id}")
	CheckDto getCheck(@PathVariable String id) {
		return service.getCheck(id);
	}

	@PostMapping(value = "/check")
	ResponseEntity<?> addCheck(@RequestBody CheckDto checkDto) {
		log.debug("checkDto: {}", checkDto);
		IRetail.ReturnCodes result = service.addCheck(checkDto);
		log.debug("Result add check: {}", result);
		if (result == IRetail.ReturnCodes.OK) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
	/***************************************************
	 * REPORTS
	 **************************************************/
	@GetMapping(value="/report/group-sales")
	List<ReportCashSaleDto> getCashSalesReport(@RequestParam(name="from") String stringFrom, 
			@RequestParam (name="to")String stringTo){
		return service.getReportCashSale(stringFrom, stringTo);
	}
	@GetMapping(value="/report/sales")
	List<DateSumDto> getSalesReport(@RequestParam(name="from") String stringFrom, 
			@RequestParam (name="to")String stringTo){
		return service.getReportSalesSumDto(stringFrom, stringTo);
	}

}
