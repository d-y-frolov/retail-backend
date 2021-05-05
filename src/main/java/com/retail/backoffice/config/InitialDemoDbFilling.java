package com.retail.backoffice.config;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.CheckDetailDto;
import com.retail.backoffice.api.CheckDto;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.UnitDto;
import com.retail.backoffice.service.interfaces.IRetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class InitialDemoDbFilling implements IDbFiller{


	
@Autowired
IRetail service;

@Override
public void FillDemoDb() { 	
	log.debug("DB Initial filling...");
	CashDto[] cashes = {
			CashDto.builder().id(CASH_ID_1).name(CASH_1).info(CASH_1_INFO).checkPrefix(CASH_1_CHECK_PREFIX).lastCheckNumber(LAST_CHECK_NUMBER).build(),
			CashDto.builder().id(CASH_ID_2).name(CASH_2).info(CASH_2_INFO).checkPrefix(CASH_2_CHECK_PREFIX).build()
	};
	service.addCacheRegister(cashes[0]);
	service.addCacheRegister(cashes[1]);
	service.addGroup(GroupDto.builder().id(GROUP_ID_1).name(GROUP_NAME_1).build());
	service.addGroup(GroupDto.builder().id(GROUP_ID_2).name(GROUP_NAME_2).build());
	service.addGroup(GroupDto.builder().id(GROUP_ID_3).name(GROUP_NAME_3).build());
	service.addUnit(UnitDto.builder().id(UNIT_ID_1).name(UNIT_NAME_1).pieceUnit(true).build());
	service.addUnit(UnitDto.builder().id(UNIT_ID_2).name(UNIT_NAME_2).pieceUnit(false).build());
	service.addUnit(UnitDto.builder().id(UNIT_ID_3).name(UNIT_NAME_3).pieceUnit(false).build());
	ProductDto[] products= {
			ProductDto.builder().id(PRODUCT_ID_1).groupId(GROUP_ID_1).name(PRODUCT_NAME_1)
			.unitId(UNIT_ID_1).tax(PRODUCT_TAX).remainder(PRODUCT_REMAINDER_1).price(PRODUCT_PRICE_1).manufacturer(PRODUCT_MANUFACTURER_1).country(PRODUCT_COUNTRY_1).build()
			,
			ProductDto.builder().id(PRODUCT_ID_2).groupId(GROUP_ID_1).name(PRODUCT_NAME_2)
			.unitId(UNIT_ID_1).tax(PRODUCT_TAX).remainder(PRODUCT_REMAINDER_2).price(PRODUCT_PRICE_2).manufacturer(PRODUCT_MANUFACTURER_2).country(PRODUCT_COUNTRY_2).build()
			,
			ProductDto.builder().id(PRODUCT_ID_3).groupId(GROUP_ID_2).name(PRODUCT_NAME_3)
			.unitId(UNIT_ID_2).tax(PRODUCT_TAX).remainder(PRODUCT_REMAINDER_3).price(PRODUCT_PRICE_3).manufacturer(PRODUCT_MANUFACTURER_3).country(PRODUCT_COUNTRY_3).build()
			,
			ProductDto.builder().id(PRODUCT_ID_4).groupId(GROUP_ID_2).name(PRODUCT_NAME_4)
			.unitId(UNIT_ID_2).tax(PRODUCT_TAX).remainder(PRODUCT_REMAINDER_4).price(PRODUCT_PRICE_4).manufacturer(PRODUCT_MANUFACTURER_4).country(PRODUCT_COUNTRY_4).build()
			,
			ProductDto.builder().id(PRODUCT_ID_5).groupId(GROUP_ID_3).name(PRODUCT_NAME_5)
			.unitId(UNIT_ID_3).tax(PRODUCT_TAX).remainder(PRODUCT_REMAINDER_5).price(PRODUCT_PRICE_5).manufacturer(PRODUCT_MANUFACTURER_5).country(PRODUCT_COUNTRY_5).build()
	};
	for (ProductDto product:products) {
		service.addProduct(product);	
	}
	
	List<CheckDetailDto> details = new ArrayList<>();
	int i=0;
	details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(QUANTITY).sum(calcNewDetailSum(products[i])).build());
	i=1;
	details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(QUANTITY).sum(calcNewDetailSum(products[i])).build());
	i=2;
	details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(QUANTITY).sum(calcNewDetailSum(products[i])).build());
	int minusDays = INITIAL_DAYS_MINUS;
	service.addCheck(CheckDto.builder()
			.cash(cashes[1]).dateTime(ZonedDateTime.now(ZoneId.of("UTC")).minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
	minusDays--;
	int detailIndex = 0;
	details.get(detailIndex).setQuantity(details.get(detailIndex).getQuantity()+4);
	details.get(detailIndex).setSum( calcDetailSum( details.get(detailIndex) ) );
	service.addCheck(CheckDto.builder()
			.cash(cashes[1]).dateTime(ZonedDateTime.now(ZoneId.of("UTC")).minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );

	minusDays--;
	details.get(detailIndex).setQuantity(details.get(detailIndex).getQuantity()-3);
	details.get(detailIndex).setSum( calcDetailSum( details.get(detailIndex) ) );
	detailIndex = 1;
	details.get(detailIndex).setQuantity(details.get(detailIndex).getQuantity()+1);
	details.get(detailIndex).setSum( calcDetailSum( details.get(detailIndex) ) );
	service.addCheck(CheckDto.builder()
			.cash(cashes[1]).dateTime(ZonedDateTime.now(ZoneId.of("UTC")).minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
	i=3;
	details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(QUANTITY).sum(calcNewDetailSum(products[i])).build());
	i=4;
	details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(QUANTITY).sum(calcNewDetailSum(products[i])).build());
	minusDays--;
	service.addCheck(CheckDto.builder()
			.cash(cashes[1]).dateTime(ZonedDateTime.now(ZoneId.of("UTC")).minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
	minusDays--;
	detailIndex = 0;
	details.get(detailIndex).setQuantity(details.get(detailIndex).getQuantity()-1);
	details.get(detailIndex).setSum( calcDetailSum( details.get(detailIndex) ) );
	service.addCheck(CheckDto.builder()
			.cash(cashes[1]).dateTime(ZonedDateTime.now(ZoneId.of("UTC")).minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
	log.debug("DB Initial filling. Done");
}
private double calcNewDetailSum(ProductDto productDto) {
	return Math.round(productDto.getPrice()*QUANTITY*100)/100.;
}
double calcDetailSum(CheckDetailDto detail) {
	return Math.round(detail.getPrice()*detail.getQuantity()*100)/100.;
}
static private Double getDetailsSum(List<CheckDetailDto> details) {
	details.forEach(d->log.debug("getDetailsSum TOTAL : {}", d ));
	double sum = Math.round( details.stream().mapToDouble(d->d.getSum()).sum() * 100 ) / 100.;
	log.debug("getDetailsSum TOTAL : {}", sum );
	return sum;
}
}
