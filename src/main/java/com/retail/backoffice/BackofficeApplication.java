package com.retail.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.UnitDto;
import com.retail.backoffice.service.IRetail;


@SpringBootApplication
public class BackofficeApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BackofficeApplication.class, args);
		IRetail service = ctx.getBean(IRetail.class);
		service.addCacheRegister(CashDto.builder().id(1).name("Cash 1").info("Info cash 1").build());
		service.addCacheRegister(CashDto.builder().id(2).name("Cash 2").info("Info cash 2").build());
		service.addGroup(GroupDto.builder().id("DR").name("Drinks").build());
		service.addGroup(GroupDto.builder().id("CH").name("Cheeses").build());
		service.addUnit(UnitDto.builder().id("PC").name("Piece").pieceUnit(true).build());
		service.addUnit(UnitDto.builder().id("KG").name("Kilogram").pieceUnit(false).build());
		service.addUnit(UnitDto.builder().id("LT").name("Liter").pieceUnit(false).build());
		service.addProduct(ProductDto.builder().id("0123456789123").groupId("DR").name("Orange juice")
				.unitId("PC").tax(17.0).remainder(20.0).price(10.99).build());
	}
}
