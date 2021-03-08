package com.retail.backoffice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.CheckDetailDto;
import com.retail.backoffice.api.CheckDto;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.UnitDto;
import com.retail.backoffice.service.interfaces.IRetail;

import ch.qos.logback.classic.net.SyslogAppender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BackofficeApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BackofficeApplication.class, args);

		log.debug("DB Initial filling");
		IRetail service = ctx.getBean(IRetail.class);
		CashDto[] cashes = {
				CashDto.builder().id(1).name("Cash 1").info("Info cash 1").build(),
				CashDto.builder().id(2).name("Cash 2").info("Info cash 2").build()
		};
		service.addCacheRegister(cashes[0]);
		service.addCacheRegister(cashes[1]);
		service.addGroup(GroupDto.builder().id("DR").name("Drinks").build());
		service.addGroup(GroupDto.builder().id("CH").name("Cheeses").build());
		service.addGroup(GroupDto.builder().id("ML").name("Dairy products").build());
		service.addUnit(UnitDto.builder().id("PC").name("Piece").pieceUnit(true).build());
		service.addUnit(UnitDto.builder().id("KG").name("Kilogram").pieceUnit(false).build());
		service.addUnit(UnitDto.builder().id("LT").name("Liter").pieceUnit(false).build());
		ProductDto[] products= {
				ProductDto.builder().id("0123456789123").groupId("DR").name("Orange juice")
				.unitId("PC").tax(17.0).remainder(20.0).price(10.99).manufacturer("J7").country("Russia").build()
				,
				ProductDto.builder().id("0123456789124").groupId("DR").name("Coke")
				.unitId("PC").tax(17.0).remainder(20.0).price(9.99).manufacturer("CokeCoke").country("Israel").build()
				,
				ProductDto.builder().id("1234567890123").groupId("CH").name("Camamber")
				.unitId("KG").tax(17.0).remainder(5.0).price(150.99).manufacturer("Camamber Сheese dairy").country("France").build()
				,
				ProductDto.builder().id("1234567890124").groupId("CH").name("Maasdam")
				.unitId("KG").tax(17.0).remainder(25.0).price(120.99).manufacturer("Cheese dairy").country("Holland").build()
				,
				ProductDto.builder().id("0000123456789").groupId("ML").name("Milk")
				.unitId("LT").tax(17.0).remainder(30.5).price(5.08).manufacturer("Tnuva").country("Israel").build()
		};
		for (ProductDto product:products) {
			service.addProduct(product);	
		}
		
		List<CheckDetailDto> details = new ArrayList<>();
		int i=0;
		double quantity = 1.;
		details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(1).sum(Math.round(products[i].getPrice()*quantity*100)/100.).build());
		i=1;
		details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(1).sum(Math.round(products[i].getPrice()*quantity*100)/100.).build());
		i=2;
		details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(1).sum(Math.round(products[i].getPrice()*quantity*100)/100.).build());
		String checkId = "0000000013";
		int minusDays = 4;
		service.addCheck(CheckDto.builder()
				.cash(cashes[1]).id(checkId).dateTime(LocalDateTime.now().minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
		checkId = "0000000014";
		minusDays = 3;
		details.get(0).setQuantity(details.get(0).getQuantity()+4);
		details.get(0).setSum(Math.round(details.get(0).getPrice()*details.get(0).getQuantity()*100)/100.);
		service.addCheck(CheckDto.builder()
				.cash(cashes[1]).id(checkId).dateTime(LocalDateTime.now().minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );

		checkId = "0000000015";
		minusDays = 2;
		details.get(0).setQuantity(details.get(0).getQuantity()-3);
		details.get(0).setSum(Math.round(details.get(0).getPrice()*details.get(0).getQuantity()*100)/100.);
		details.get(1).setQuantity(details.get(1).getQuantity()+1);
		details.get(1).setSum(Math.round(details.get(1).getPrice()*details.get(1).getQuantity()*100)/100.);
		service.addCheck(CheckDto.builder()
				.cash(cashes[1]).id(checkId).dateTime(LocalDateTime.now().minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
		i=3;
		details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(1).sum(Math.round(products[i].getPrice()*quantity*100)/100.).build());
		i=4;
		details.add(CheckDetailDto.builder().productDto(products[i]).price(products[i].getPrice()).quantity(1).sum(Math.round(products[i].getPrice()*quantity*100)/100.).build());
		checkId = "0000000016";
		minusDays = 1;
		service.addCheck(CheckDto.builder()
				.cash(cashes[1]).id(checkId).dateTime(LocalDateTime.now().minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
		checkId = "0000000017";
		minusDays = 0;
		details.get(0).setQuantity(details.get(0).getQuantity()-1);
		details.get(0).setSum(Math.round(details.get(0).getPrice()*details.get(0).getQuantity()*100)/100.);
		service.addCheck(CheckDto.builder()
				.cash(cashes[1]).id(checkId).dateTime(LocalDateTime.now().minusDays(minusDays)).sum(getDetailsSum(details)).details(details).build() );
		
	}
	static private Double getDetailsSum(List<CheckDetailDto> details) {
		System.out.println("============= SUM DETAILS=======");
		details.forEach(System.out::println);
		double sum = Math.round( details.stream().mapToDouble(d->d.getSum()).sum() * 100 ) / 100.;
		System.out.printf("======== TOTAL : %f\n", sum );
		return sum;
	}
}
