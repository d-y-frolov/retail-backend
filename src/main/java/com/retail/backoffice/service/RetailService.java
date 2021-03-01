package com.retail.backoffice.service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.CheckDetailDto;
import com.retail.backoffice.api.CheckDto;
import com.retail.backoffice.api.DateSumDto;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.ReportCashSaleDto;
import com.retail.backoffice.api.UnitDto;
import com.retail.backoffice.controllers.Controller;
import com.retail.backoffice.domain.entities.Cash;
import com.retail.backoffice.domain.entities.CheckDetail;
import com.retail.backoffice.domain.entities.Checks;
import com.retail.backoffice.domain.entities.Groups;
import com.retail.backoffice.domain.entities.Product;
import com.retail.backoffice.domain.entities.SalesDateSumEntity;
import com.retail.backoffice.domain.entities.Unit;
import com.retail.backoffice.domain.repo.CashRepo;
import com.retail.backoffice.domain.repo.CheckRepo;
import com.retail.backoffice.domain.repo.DetailRepo;
import com.retail.backoffice.domain.repo.GroupRepo;
import com.retail.backoffice.domain.repo.ProductRepo;
import com.retail.backoffice.domain.repo.SalesDateSumRepo;
import com.retail.backoffice.domain.repo.UnitRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RetailService implements IRetail {
	@Autowired
	ProductRepo productRepo;
	@Autowired
	GroupRepo groupRepo;
	@Autowired
	UnitRepo unitRepo;
	@Autowired
	CashRepo cashRepo;
	@Autowired
	CheckRepo checkRepo;
	@Autowired
	DetailRepo detailRepo;
	@Autowired
	SalesDateSumRepo salesDateSumRepo;

	/*************************************
	 * GROUPS
	 *************************************/
	@Override
	public GroupDto getGroup(String id) {
		Groups group = groupRepo.findById(id).orElse(null);
		if (group == null)
			return null;
		return GroupDto.builder().id(group.getId()).name(group.getName()).build();
	}

	@Override
	public List<GroupDto> getAllGroups() {
		return groupRepo.findAll().stream().map(this::mapGroupsToGroupDto).collect(Collectors.toList());
	}

	private GroupDto mapGroupsToGroupDto(Groups groups) {
		if (groups == null) {
			return new GroupDto("", "");
		}
		return GroupDto.builder().id(groups.getId()).name(groups.getName()).build();
	}

	@Transactional
	@Override
	public ReturnCodes addGroup(GroupDto groupDto) {
		if (groupRepo.existsById(groupDto.getId())) {
			return ReturnCodes.GROUP_ALREADY_EXISTS;
		}
		groupRepo.save(new Groups(groupDto.getId(), groupDto.getName()));
		return ReturnCodes.OK;
	}

	@Override
	public ReturnCodes updateGroup(GroupDto groupDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCodes removeGroup(GroupDto groupDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/*************************************
	 * PRODUCTS
	 *************************************/
	@Override
	public ProductDto getProduct(String id) {
		Product product = productRepo.findById(id).orElse(null);
		if (product == null) {
			return null;
		}
		return mapProductToProductDto(product);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		return productRepo.findAll().stream().map(this::mapProductToProductDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> getSearchedProducts(String searchString) {
		List<Product> products = null;
		if (searchString==null || searchString.isEmpty()) {
			products = productRepo.findAll();
		}
		if (searchString.matches("\\d+")) {
			products = productRepo.searchByIdOrName(searchString.trim()); 
		}else{
			products = productRepo.searchByName(searchString.trim().toUpperCase());
		}
		return products.stream().map(this::mapProductToProductDto).collect(Collectors.toList());
	}

	private ProductDto mapProductToProductDto(Product product) {
		return ProductDto.builder().id(product.getId()).name(product.getName()).groupId(product.getGroup().getId())
				.groupName(product.getGroup().getName()).unitId(product.getUnit().getId())
				.unitName(product.getUnit().getName()).isPieceUnit(product.getUnit().isPieceUnit())
				.price(product.getPrice()).remainder(product.getRemainder()).tax(product.getTax())
				.country(product.getCountry()).manufacturer(product.getManufacturer()).build();
	}

	@Transactional
	@Override
	public ReturnCodes addProduct(ProductDto productDto) {
		if (productDto == null || productDto.getGroupId() == null || productDto.getUnitId() == null) {
			return ReturnCodes.INPUT_OBJECT_IS_NULL;
		}
		if (productRepo.existsById(productDto.getId())) {
			return ReturnCodes.PRODUCT_ID_ALREADY_EXISTS;
		}
		Groups groups = groupRepo.findById(productDto.getGroupId()).orElse(null);
		if (groups == null) {
			return ReturnCodes.GROUP_NOT_FOUND;
		}
		Unit unit = unitRepo.findById(productDto.getUnitId()).orElse(null);
		if (unit == null) {
			return ReturnCodes.UNIT_NOT_FOUND;
		}

		productRepo.save(mapProductDtoToProduct(productDto, groups, unit));
		return ReturnCodes.OK;
	}

	private Product mapProductDtoToProduct(ProductDto productDto, Groups groups, Unit unit) {
		return Product.builder().id(productDto.getId()).name(productDto.getName()).group(groups).unit(unit)
				.price(productDto.getPrice()).remainder(productDto.getRemainder()).tax(productDto.getTax())
				.country(productDto.getCountry()).manufacturer(productDto.getManufacturer()).build();
	}

	@Transactional
	@Override
	public ReturnCodes updateProduct(ProductDto productDto) {
		if (productDto == null || productDto.getGroupId() == null || productDto.getUnitId() == null) {
			return ReturnCodes.INPUT_OBJECT_IS_NULL;
		}
		if (!productRepo.existsById(productDto.getId())) {
			return ReturnCodes.PRODUCT_ID_NOT_FOUND;
		}
		Groups groups = groupRepo.findById(productDto.getGroupId()).orElse(null);
		if (groups == null) {
			return ReturnCodes.GROUP_NOT_FOUND;
		}
		Unit unit = unitRepo.findById(productDto.getUnitId()).orElse(null);
		if (unit == null) {
			return ReturnCodes.UNIT_NOT_FOUND;
		}

		productRepo.save(mapProductDtoToProduct(productDto, groups, unit));
		return ReturnCodes.OK;
	}

	@Transactional
	@Override
	public ReturnCodes removeProduct(String id) {
		if (!productRepo.existsById(id)) {
			return ReturnCodes.PRODUCT_ID_NOT_FOUND;
		}
		try {
			productRepo.deleteById(id);
			return ReturnCodes.OK;
		}catch(Exception e) {
			return ReturnCodes.CANNOT_BE_REMOVED_DEPENDENCIES_EXIST;
		}
	}

	/*************************************
	 * UNITS
	 *************************************/
	@Override
	public List<UnitDto> getAllUnits() {
		return unitRepo.findAll().stream().map(u -> mapUnitToUnitDto(u)).collect(Collectors.toList());
	}

	private UnitDto mapUnitToUnitDto(Unit unit) {
		return UnitDto.builder().id(unit.getId()).name(unit.getName()).pieceUnit(unit.isPieceUnit()).build();
	}

	@Override
	public UnitDto getUnit(String id) {
		Unit unit = unitRepo.findById(id).orElse(null);
		if (unit == null) {
			return null;
		}
		return mapUnitToUnitDto(unit);
	}

	@Transactional
	@Override
	public ReturnCodes addUnit(UnitDto unitDto) {
		if (unitDto == null) {
			return ReturnCodes.INPUT_OBJECT_IS_NULL;
		}
		if (unitRepo.existsById(unitDto.getId())) {
			return ReturnCodes.UNIT_ALREADY_EXISTS;
		}
		unitRepo.save(mapUnitDtoToUnit(unitDto));
		return ReturnCodes.OK;
	}

	private Unit mapUnitDtoToUnit(UnitDto unitDto) {
		return Unit.builder().id(unitDto.getId()).name(unitDto.getName()).pieceUnit(unitDto.isPieceUnit()).build();
	}

	@Override
	public ReturnCodes updateUnit(UnitDto unitDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCodes removeUnit(UnitDto unitDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/**************************************************
	 * CASHES
	 ***************************************************/
	@Override
	public List<CashDto> getAllCacheRegisters() {
		return cashRepo.findAll().stream().map(cash -> mapCashToCashDto(cash)).collect(Collectors.toList());
	}

	private CashDto mapCashToCashDto(Cash cash) {
		return CashDto.builder().id(cash.getId()).name(cash.getName()).info(cash.getInfo()).build();
	}

	@Override
	public CashDto getCacheRegister(int id) {
		Cash cash = cashRepo.findById(id).orElse(null);
		if (cash == null) {
			return null;
		}
		return mapCashToCashDto(cash);
	}

	@Transactional
	@Override
	public ReturnCodes addCacheRegister(CashDto cashDto) {
		if (cashDto == null) {
			return ReturnCodes.INPUT_OBJECT_IS_NULL;
		}
		if (cashRepo.existsById(cashDto.getId())) {
			return ReturnCodes.CASH_ALREADY_EXISTS;
		}
		cashRepo.save(mapCashDtoToCash(cashDto));
		return ReturnCodes.OK;
	}

	private Cash mapCashDtoToCash(CashDto cashDto) {
		return Cash.builder().id(cashDto.getId()).name(cashDto.getName()).info(cashDto.getInfo()).build();
	}

	@Override
	public ReturnCodes updateCacheRegister(CashDto cashDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCodes removeCacheRegister(CashDto cashDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/**************************************************
	 * CHECKS
	 ***************************************************/
	@Override
	public List<CheckDto> getAllChecks() {
		return checkRepo.findAll().stream().map(check -> mapCheckToCheckDto(check)).collect(Collectors.toList());
	}

	private CheckDto mapCheckToCheckDto(Checks check) {
		return CheckDto.builder().id(check.getId()).cash(mapCashToCashDto(check.getCash()))
				.dateTime(check.getDateTime()).details(mapCashDetailsToCashDetailDto(check.getDetails())).build();
	}

	private List<CheckDetailDto> mapCashDetailsToCashDetailDto(List<CheckDetail> cashDetails) {
		if (cashDetails == null) {
			return new ArrayList<>();
		}
		return cashDetails.stream().map(checkDetail -> mapCheckDetailToCheckDetailDto(checkDetail))
				.collect(Collectors.toList());
	}

	private CheckDetailDto mapCheckDetailToCheckDetailDto(CheckDetail checkDetail) {
		return CheckDetailDto.builder().productDto(mapProductToProductDto(checkDetail.getProduct()))
				.quantity(checkDetail.getQuantity()).price(checkDetail.getPrice()).sum(checkDetail.getSum()).build();
	}

	@Override
	public CheckDto getCheck(String id) {
		Checks check = checkRepo.findById(id).orElse(null);
		if (check == null) {
			return null;
		}
		return mapCheckToCheckDto(check);
	}

	@Transactional
	@Override
	public ReturnCodes addCheck(CheckDto checkDto) {
		if (checkDto == null || checkDto.getCash() == null || checkDto.getDetails() == null) {
			return ReturnCodes.INPUT_OBJECT_IS_NULL;
		}
		if (checkRepo.existsById(checkDto.getId())) {
			return ReturnCodes.CHECK_ID_ALREADY_EXISTS;
		}
		if (!cashRepo.existsById(checkDto.getCash().getId())) {
			log.debug("CASH_ID: {}", checkDto.getCash().getId() );
			return ReturnCodes.CASH_NOT_FOUND;
		}
		Checks check = checkRepo.save(mapCheckDtoToCheck(checkDto));
		List<CheckDetail> details = mapListCheckDetailToListDetail(checkDto.getDetails(), check);
		changeProductRemainders(details, false);
		detailRepo.saveAll(details);
		return ReturnCodes.OK;
	}

	private void changeProductRemainders(List<CheckDetail> details, boolean isIncreaseRemainder) {
		details.forEach(d -> d.getProduct()
				.setRemainder(d.getProduct().getRemainder() + (isIncreaseRemainder ? 1 : -1) * d.getQuantity()));
	}

	private Checks mapCheckDtoToCheck(CheckDto checkDto) {
		return Checks.builder().id(checkDto.getId()).cash(mapCashDtoToCash(checkDto.getCash()))
				.dateTime(checkDto.getDateTime()).sum(checkDto.getSum()).build();
	}

	private List<CheckDetail> mapListCheckDetailToListDetail(List<CheckDetailDto> details, Checks check) {
		return details.stream().map(detailDto -> mapCheckDetailDto2CheckDetail(detailDto, check))
				.collect(Collectors.toList());
	}

	private CheckDetail mapCheckDetailDto2CheckDetail(CheckDetailDto detailDto, Checks check)
			throws InvalidParameterException {
		Product product = productRepo.findById(detailDto.getProductDto().getId()).orElse(null);
		if (product == null) {
			throw new InvalidParameterException("PRODUCT NOT FOUND");
		}
		return CheckDetail.builder().check(check).product(product).price(detailDto.getPrice())
				.quantity(detailDto.getQuantity()).sum(detailDto.getSum()).build();
	}

	@Override
	public ReturnCodes updateCheck(CheckDto checkDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCodes removeCheck(CheckDto checkDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/**************************************************
	 * REPORT sales by groups, dates
	 ***************************************************/
	@Override
	public List<ReportCashSaleDto> getReportCashSale(String startDate, String endDate) {
		List <Object[]> rawData = detailRepo.getReportCashSale(startDate, endDate);
		List<ReportCashSaleDto> listReportCashSaleDto = new ArrayList<>();
		String groupId = "";
		ReportCashSaleDto reportCashSaleDto = null;
		List<DateSumDto> data = null;
		for (Object[] row:rawData) {
			if (!groupId.equals((String)row[0])) {
				if (reportCashSaleDto !=null ) {
					listReportCashSaleDto.add(reportCashSaleDto);
				}
				data = new ArrayList<>();
				reportCashSaleDto = 
					ReportCashSaleDto.builder()
					.groupId((String)row[0])
					.groupName((String)row[1])
					.data(data)
					.build();
				groupId = (String)row[0];
			}
			data.add(DateSumDto.builder()
					.stringDate((String)row[2])
					.sum((double)row[3])
					.build());
		}
		if (reportCashSaleDto !=null ) {
			listReportCashSaleDto.add(reportCashSaleDto);
		}
		return listReportCashSaleDto;
	}

	
	/**************************************************
	 * REPORT sales by dates
	 ***************************************************/
	@Override
	public List<DateSumDto> getReportSalesSumDto(String startDate, String endDate) {
		return salesDateSumRepo.getSalesDateSum(startDate, endDate)
				.stream().map(this::mapSalesDateSumEntityToDateSumDto).collect(Collectors.toList());
	}
	
	private DateSumDto mapSalesDateSumEntityToDateSumDto(SalesDateSumEntity entity) {
		return DateSumDto.builder().stringDate(entity.getStringDate()).sum(entity.getSum()).build();
	}
}
