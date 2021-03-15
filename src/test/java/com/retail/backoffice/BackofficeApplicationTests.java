package com.retail.backoffice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.retail.backoffice.api.CheckDetailDto;
import com.retail.backoffice.api.CheckDto;
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
	private static final String GROUP_ID_NOT_EXISTS = "GROUP_ID_NOT_EXISTS";
	private static final String GROUP_NAME = "GROUP_NAME";
	private static final String UNIT_ID = "UNIT_ID";
	private static final String UNIT_ID_NOT_EXISTS = "UNIT_ID_NOT_EXISTS";
	private static final String UNIT_NAME = "UNIT_NAME";
	private static final boolean UNIT_PIECE = true;
	private static final String PRODUCT_ID = "PRODUCT_ID";
	private static final String PRODUCT_ID_NOT_EXISTS = "PRODUCT_ID_WRONG";
	private static final String PRODUCT_ID_SEARCH_STRING = "_I";
	private static final String PRODUCT_NAME = "PRODUCT_NAME";
	private static final String PRODUCT_NAME_SEARCH_STRING = "CT_NA";
	private static final String PRODUCT_ID_NAME_WRONG_SEARCH_STRING = PRODUCT_ID+PRODUCT_NAME;
	private static final String PRODUCT_MANUFACTURER = "PRODUCT_MANUFACTURER";
	private static final String PRODUCT_COUNTRY = "PRODUCT_COUNTRY";
	private static final double PRODUCT_REMAINDER = 10.;
	private static final double PRODUCT_PRICE = 100.99;
	private static final double PRODUCT_TAX = 17.;
	private static final String CHECK_ID_WRONG = "CHECK_ID_WRONG";
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
	private CheckDto checkDto; 
	private CheckDetailDto checkDetailDto; 
	
	@BeforeAll
	void beforeAll() {
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
		checkDetailDto = CheckDetailDto.builder().productDto(productDto).price(CHECK_PRICE)
				.quantity(CHECK_QUANTITY).sum(CHECK_PRICE * CHECK_QUANTITY).build();
		List<CheckDetailDto> details = new ArrayList<>();
		details.add(checkDetailDto);
		checkDto = CheckDto.builder().cash(cashDto).dateTime(CHECK_DATETIME)
				.details(details).sum(CHECK_PRICE * CHECK_QUANTITY).build();
		
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

		response = restTemplate.exchange(checkURL,
				HttpMethod.POST, new HttpEntity<CheckDto>(checkDto ),
				Object.class
				);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@BeforeEach
	void beforeEach() {
	}

	@Test
	void contextLoads() {
		assertNotNull(controller);		
	}
	
	/****
	 *    PRODUCT GROUPS
	 */
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

	/*****
	 *	 UNITS
	 */
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

	/**
	 *   PRODUCTS 
	 */
	@Test
	void testProductsRestRequests() {
		// Get all products
		ResponseEntity <List<ProductDto>> productResponse = restTemplate.exchange(productURL,
			HttpMethod.GET, null,
			new ParameterizedTypeReference<List<ProductDto>>(){});
		assertTrue(productResponse.getBody().get(0).getId().equals(PRODUCT_ID));
		
		// Get products by search string
		// CASE : search string is part of the id (bar-code)
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
		// CASE : search string is not part of the id (bar-code) and is not part of the name   
		productResponse = restTemplate.exchange(productURL+"?search="+PRODUCT_ID_NAME_WRONG_SEARCH_STRING,
				HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ProductDto>>(){});
		assertEquals(0, productResponse.getBody().size());
		
		
		// get product by id
		//CASE : the product exists
		ProductDto productDtoResonse = restTemplate.getForObject(productURL+"/"+PRODUCT_ID, 
				ProductDto.class);
		assertEquals(productDto.getId(), productDtoResonse.getId());
		//CASE : the product does not exist
		ErrorResponseDto errorResonseDto = restTemplate.getForObject(productURL+"/"+PRODUCT_ID_NOT_EXISTS, 
				ErrorResponseDto.class);
		assertNotNull(errorResonseDto);
		assertEquals(HttpStatus.CONFLICT.value(), errorResonseDto.getStatus());
		assertTrue(errorResonseDto.getMessage().contains(IRetail.ReturnCodes.PRODUCT_ID_NOT_FOUND.toString()));
		assertTrue(errorResonseDto.getMessage().contains(PRODUCT_ID_NOT_EXISTS));

		//add product
		// CASE add product with existing bar-code (id)
		errorResonseDto = restTemplate.postForObject(productURL,productDto,ErrorResponseDto.class);
		assertNotNull(errorResonseDto);
		assertEquals(HttpStatus.CONFLICT.value(), errorResonseDto.getStatus());
		assertTrue(errorResonseDto.getMessage().contains(IRetail.ReturnCodes.PRODUCT_ID_ALREADY_EXISTS.toString()));
		//CASE add product with not existing groupId
		ProductDto productDtoWrongGroupId = ProductDto.builder()
				.id(PRODUCT_ID_NOT_EXISTS)
				.name(PRODUCT_NAME)
				.groupId(GROUP_ID_NOT_EXISTS)
				.unitId(UNIT_ID)
				.build();
		errorResonseDto = restTemplate.postForObject(productURL,productDtoWrongGroupId,ErrorResponseDto.class);
		assertNotNull(errorResonseDto);
		assertEquals(HttpStatus.CONFLICT.value(), errorResonseDto.getStatus());
		assertTrue(errorResonseDto.getMessage().contains(IRetail.ReturnCodes.GROUP_NOT_FOUND.toString()));
		//CASE add product with not existing unitId
		ProductDto productDtoWrongUnitId = ProductDto.builder()
				.id(PRODUCT_ID_NOT_EXISTS)
				.name(PRODUCT_NAME)
				.groupId(GROUP_ID)
				.unitId(UNIT_ID_NOT_EXISTS)
				.build();
		errorResonseDto = restTemplate.postForObject(productURL,productDtoWrongUnitId,ErrorResponseDto.class);
		assertNotNull(errorResonseDto);
		assertEquals(HttpStatus.CONFLICT.value(), errorResonseDto.getStatus());
		assertTrue(errorResonseDto.getMessage().contains(IRetail.ReturnCodes.UNIT_NOT_FOUND.toString()));
		//CASE add product without bar-code(id)
		ProductDto productDtoWithoutId = ProductDto.builder()
				.name(PRODUCT_NAME)
				.groupId(GROUP_ID)
				.unitId(UNIT_ID)
				.build();
		ResponseEntity<?> response = restTemplate.exchange(productURL,
				HttpMethod.POST, new HttpEntity<ProductDto>(productDtoWithoutId ),
				Object.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	
	/*****
	 *    CASH REGISTERS
	 */
	@Test
	void testCashesRestRequests() {
		// get all cash registers
		ResponseEntity <List<CashDto>> cashResponse = restTemplate.exchange(cashURL,
		HttpMethod.GET, null,
		new ParameterizedTypeReference<List<CashDto>>(){});
		assertTrue(cashResponse.getBody().get(0).getId()==CASH_ID);
		
		//get an existing cash register
		CashDto cash = restTemplate.getForObject(cashURL+"/"+CASH_ID, CashDto.class);
		assertEquals(cashDto.getId(), cash.getId());

		// get a not existing cash register
		cash = restTemplate.getForObject(unitURL+"/"+CASH_ID+"_", CashDto.class);
		assertNull(cash);
		
		// try to append an existing group
		ResponseEntity<?> response = restTemplate.exchange(cashURL,
				HttpMethod.POST, new HttpEntity<CashDto>(cashDto ),
				Object.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	
	
	/**********
	 *    CHECKS
	 */
	@Test
	void testChecksRestRequests() {
		//get all checks
		ResponseEntity<List<CheckDto>> checksResponse = restTemplate.exchange(checkURL, HttpMethod.GET,null, new ParameterizedTypeReference<List<CheckDto>>(){});
		assertNotNull(checksResponse);
		assertEquals(1, checksResponse.getBody().size());
		assertEquals(cashDto.getId(), checksResponse.getBody().get(0).getCash().getId());
		assertEquals(1, checksResponse.getBody().get(0).getDetails().size());
		assertTrue(checksResponse.getBody().get(0).getDetails().get(0).getProductDto().getId().equals(productDto.getId()));
		String checkId = checksResponse.getBody().get(0).getId();
		
		//get an existing check
		CheckDto check = restTemplate.getForObject(checkURL+"/"+checkId, CheckDto.class);
		assertNotNull(check);
		assertEquals(checkId, check.getId());

		//get not existing check
		ErrorResponseDto errorResponseDto = restTemplate.getForObject(checkURL+"/"+CHECK_ID_WRONG, ErrorResponseDto.class);
		assertNotNull(errorResponseDto);
		assertTrue(errorResponseDto.getMessage().contains(IRetail.ReturnCodes.CHECK_ID_NOT_FOUND.toString()));
		
		// try to add check with not existing CASH_ID
		CheckDto checkDtoWrong = CheckDto.builder().dateTime(CHECK_DATETIME).cash(null).details(checkDto.getDetails()).sum(checkDto.getSum()).build(); 
		ResponseEntity<?> response = restTemplate.exchange(checkURL,
				HttpMethod.POST, new HttpEntity<CheckDto>(checkDtoWrong ),
				Object.class
				);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		// try to add check with not existing details
		checkDtoWrong = CheckDto.builder().dateTime(CHECK_DATETIME).cash(cashDto).details(null).build(); 
		response = restTemplate.exchange(checkURL,
				HttpMethod.POST, new HttpEntity<CheckDto>(checkDtoWrong ),
				Object.class
				);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

}
