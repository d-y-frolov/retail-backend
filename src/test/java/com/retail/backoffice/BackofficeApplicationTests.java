package com.retail.backoffice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.retail.backoffice.api.CashDto;
import com.retail.backoffice.api.ErrorResponseDto;
import com.retail.backoffice.api.GroupDto;
import com.retail.backoffice.api.ProductDto;
import com.retail.backoffice.api.UnitDto;
import com.retail.backoffice.controllers.Controller;
import com.retail.backoffice.service.interfaces.IRetail;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class BackofficeApplicationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private Controller controller;
	@Autowired
	private TestRestTemplate restTemplate;

	private static final String GROUP_ID = "GROUP_ID";
	private static final String GROUP_NAME = "GROUP_NAME";
	private static final String UNIT_ID = "ID_UNIT";
	private static final String UNIT_NAME = "UNIT_NAME";
	private static final boolean UNIT_PIECE = true;
	private static final String PRODUCT_ID = "PRODUCT_ID";
	private static final String PRODUCT_ID_SEARCH_STRING = "_I";
	private static final String PRODUCT_NAME = "PRODUCT_NAME";
	private static final String PRODUCT_NAME_SEARCH_STRING = "CT_NA";
	private static final String PRODUCT_ID_NAME_WRONG_SEARCH_STRING = PRODUCT_ID+PRODUCT_NAME;
	private static final String PRODUCT_MANUFACTURER = "PRODUCT_MANUFACTURER";
	private static final String PRODUCT_COUNTRY = "PRODUCT_COUNTRY";
	private static final double PRODUCT_REMAINDER = 10.;
	private static final double PRODUCT_PRICE = 100.99;
	private static final double PRODUCT_TAX = 17.;
	private static final String CHECK_ID = "CHECK_ID";
	private static final LocalDateTime CHECK_DATETIME = LocalDateTime.now();
	private static final double CHECK_PRICE = PRODUCT_PRICE;
	private static final double CHECK_QUANTITY = 1;
	private static final int CASH_ID = 1; 
	private static final String CASH_NAME = "CASH_NAME"; 
	private static final String CASH_INFO = "CASH_INFO"; 
	
	private static final String BASE_URL = "http://localhost"; 
	private static final String GROUP_ENDPOINT = "/group"; 
	private static final String UNIT_ENDPOINT = "/unit"; 
	private static final String PRODUCT_ENDPOINT = "/product"; 
	private static final String CASH_ENDPOINT = "/cash-register"; 
	private static final String CHECK_ENDPOINT = "/check"; 
	private static final String REPORT_SALES_ENDPOINT = "/sales"; 
	private static final String REPORT_GROUP_SALES_ENDPOINT = "/group-sales"; 
	static String groupURL; 
	static String unitURL; 
	static String productURL; 
	static String cashURL; 
	static String checkURL; 
	static String reportSalesURL; 
	static String reportGroupSalesURL; 
	
	private GroupDto groupDto; 
	private UnitDto unitDto; 
	private ProductDto productDto; 
	private CashDto cashDto; 
	
	@BeforeAll
	void beforeAll() {
		assertNotNull(controller);		
		
		groupURL = String.format("%s:%d%s", BASE_URL, port, GROUP_ENDPOINT); 
		unitURL = String.format("%s:%d%s", BASE_URL, port, UNIT_ENDPOINT); 
		productURL= String.format("%s:%d%s", BASE_URL, port, PRODUCT_ENDPOINT); 
		cashURL= String.format("%s:%d%s", BASE_URL, port, CASH_ENDPOINT); 
		checkURL= String.format("%s:%d%s", BASE_URL, port, CHECK_ENDPOINT); 
		reportSalesURL= String.format("%s:%d%s", BASE_URL, port, REPORT_SALES_ENDPOINT); 
		reportGroupSalesURL= String.format("%s:%d%s", BASE_URL, port, REPORT_GROUP_SALES_ENDPOINT); 

		groupDto = GroupDto.builder().id(GROUP_ID).name(GROUP_NAME).build();
		unitDto = UnitDto.builder().id(UNIT_ID).name(UNIT_NAME).pieceUnit(UNIT_PIECE).build();
		productDto = ProductDto.builder().id(PRODUCT_ID).name(PRODUCT_NAME)
				.groupId(GROUP_ID).groupName(GROUP_NAME)
				.unitId(UNIT_ID).unitName(UNIT_NAME).isPieceUnit(UNIT_PIECE)
				.price(PRODUCT_PRICE).remainder(PRODUCT_REMAINDER).tax(PRODUCT_TAX)
				.manufacturer(PRODUCT_MANUFACTURER).country(PRODUCT_COUNTRY).build();
		cashDto = CashDto.builder().id(CASH_ID).name(CASH_NAME).info(CASH_INFO).build();
		
		ResponseEntity <?> response = restTemplate.exchange(groupURL,
				HttpMethod.POST, new HttpEntity<GroupDto>(groupDto ),
				Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		response = restTemplate.exchange(unitURL,
				HttpMethod.POST, new HttpEntity<UnitDto>(unitDto ),
				Object.class
				);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		response = restTemplate.exchange(cashURL,
				HttpMethod.POST, new HttpEntity<CashDto>(cashDto ),
				Object.class
				);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		response = restTemplate.exchange(productURL,
				HttpMethod.POST, new HttpEntity<ProductDto>(productDto ),
				Object.class
				);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@BeforeEach
	void beforeEach() {
	}

//	@Test
//	void contextLoads() {
//		assertNotNull(controller);		
//	}
	
	@Test
	void testGroupsRestRequests() {
		// get all groups
		ResponseEntity <List<GroupDto>> groupsResponse = restTemplate.exchange(groupURL,
		HttpMethod.GET, null,
		new ParameterizedTypeReference<List<GroupDto>>(){});
		assertTrue(groupsResponse.getBody().contains(groupDto));

		// get an existing group
		ResponseEntity <GroupDto> groupResponse = restTemplate.exchange(groupURL+"/"+GROUP_ID,
		HttpMethod.GET, null,
		GroupDto.class);
		assertEquals(groupDto, groupResponse.getBody());

		// get a not existing group
		ResponseEntity <ErrorResponseDto> response = restTemplate.exchange(groupURL+"/"+GROUP_ID+"_",
		HttpMethod.GET, null,
		ErrorResponseDto.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertTrue( response.getBody().getMessage()
				.contains(IRetail.ReturnCodes.GROUP_NOT_FOUND.toString()) );

		// try to append an existing group
		response = restTemplate.exchange(groupURL,
				HttpMethod.POST, new HttpEntity<GroupDto>(groupDto ),
				ErrorResponseDto.class);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertTrue( response.getBody().getMessage()
				.contains(IRetail.ReturnCodes.GROUP_ALREADY_EXISTS.toString()) );

		// try to append a group with empty id 
		response = restTemplate.exchange(groupURL,
				HttpMethod.POST, new HttpEntity<GroupDto>(GroupDto.builder().build() ),
				ErrorResponseDto.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue( response.getBody().getMessage()
				.contains(IRetail.ReturnCodes.INPUT_OBJECT_IS_NULL.toString()) );
	}
	
	
	@Test
	void testUnitsRestRequests() {
		// get all units
		ResponseEntity <List<UnitDto>> unitsResponse = restTemplate.exchange(unitURL,
		HttpMethod.GET, null,
		new ParameterizedTypeReference<List<UnitDto>>(){});
		assertTrue(unitsResponse.getBody().contains(unitDto));
		
		// get an existing unit
		UnitDto  unit = restTemplate.getForObject(unitURL+"/"+UNIT_ID, UnitDto.class);
		assertEquals(unitDto, unit);

		// get a not existing unit
		unit = restTemplate.getForObject(unitURL+"/"+UNIT_ID+"_", UnitDto.class);
		assertNull(unit);
		
		// try to append an existing group
		ResponseEntity<?> response = restTemplate.exchange(unitURL,
				HttpMethod.POST, new HttpEntity<UnitDto>(unitDto ),
				Object.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	

	@Test
	void testProductsRestRequests() {
		// Get all products
		ResponseEntity <List<ProductDto>> productResponse = restTemplate.exchange(productURL,
			HttpMethod.GET, null,
			new ParameterizedTypeReference<List<ProductDto>>(){});
		assertTrue(productResponse.getBody().contains(productDto));
		
		// Get products by search string
		// CASE : search string is part of the id (barcode)
		productResponse = restTemplate.exchange(productURL+"?search="+PRODUCT_ID_SEARCH_STRING,
			HttpMethod.GET, null,
			new ParameterizedTypeReference<List<ProductDto>>(){});
		productResponse.getBody().forEach(p->
			assertTrue( p.getId().contains(PRODUCT_ID_SEARCH_STRING)|| 
			 p.getName().contains(PRODUCT_ID_SEARCH_STRING)) );
		// CASE : search string is part of the name 
		productResponse = restTemplate.exchange(productURL+"?search="+PRODUCT_NAME_SEARCH_STRING,
				HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProductDto>>(){});
			productResponse.getBody().forEach(p->
				assertTrue( p.getId().contains(PRODUCT_ID_SEARCH_STRING)|| 
				 p.getName().contains(PRODUCT_ID_SEARCH_STRING)) );
		// CASE : search string is not part of the id (barcode) and is not part of the name   
		productResponse = restTemplate.exchange(productURL+"?search="+PRODUCT_ID_NAME_WRONG_SEARCH_STRING,
				HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProductDto>>(){});
		assertEquals(0, productResponse.getBody().size());
		
		// get product by id
		
	}
	
	
	@Test
	void testCashesRestRequests() {
		// get all cash registers
		ResponseEntity <List<CashDto>> cashResponse = restTemplate.exchange(cashURL,
		HttpMethod.GET, null,
		new ParameterizedTypeReference<List<CashDto>>(){});
		assertTrue(cashResponse.getBody().contains(cashDto));
		
		//get an existing cash register
		CashDto cash = restTemplate.getForObject(cashURL+"/"+CASH_ID, CashDto.class);
		assertEquals(cashDto, cash);

		// get a not existing cash register
		cash = restTemplate.getForObject(unitURL+"/"+CASH_ID+"_", CashDto.class);
		assertNull(cash);
		
		// try to append an existing group
		ResponseEntity<?> response = restTemplate.exchange(cashURL,
				HttpMethod.POST, new HttpEntity<CashDto>(cashDto ),
				Object.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	@Test
	void testChecksRestRequests() {
		System.out.println("**************Checks:");
	}

}
