package com.retail.backoffice.config;

public interface IDbFiller {

	void FillDemoDb();
	
	int CASH_ID_1 = 1;	
	int CASH_ID_2 = 2;	
	String CASH_1 = "Cash 1";	
	String CASH_2 = "Cash 2";	
	int LAST_CHECK_NUMBER = 100;	
	String CASH_1_INFO = "Clothing store";	
	String CASH_2_INFO = "Grocery department";	
	String CASH_1_CHECK_PREFIX = "CS";	
	String CASH_2_CHECK_PREFIX = "GD";
	String GROUP_ID_1 = "DR";
	String GROUP_ID_2 = "CH";
	String GROUP_ID_3 = "DP";
	String GROUP_NAME_1 = "Drinks";
	String GROUP_NAME_2 = "Cheeses";
	String GROUP_NAME_3 = "Dairy products";
	String UNIT_ID_1 = "PC";
	String UNIT_ID_2 = "KG";
	String UNIT_ID_3 = "LT";
	String UNIT_NAME_1 = "Piece";
	String UNIT_NAME_2 = "Kilogram";
	String UNIT_NAME_3 = "Liter";
	String PRODUCT_ID_1 = "0123456789123";
	String PRODUCT_ID_2 = "0123456789124";
	String PRODUCT_ID_3 = "1234567890123";
	String PRODUCT_ID_4 = "1234567890124";
	String PRODUCT_ID_5 = "0000123456789";
	String PRODUCT_NAME_1 = "Orange juice";
	String PRODUCT_NAME_2 = "Coke";
	String PRODUCT_NAME_3 = "Camamber";
	String PRODUCT_NAME_4 = "Maasdamer";
	String PRODUCT_NAME_5 = "Milk";
	double PRODUCT_PRICE_1 = 10.99;
	double PRODUCT_PRICE_2 = 9.99;
	double PRODUCT_PRICE_3 = 150.99;
	double PRODUCT_PRICE_4 = 120.99;
	double PRODUCT_PRICE_5 = 5.08;
	double PRODUCT_TAX = 17.;
	double PRODUCT_REMAINDER_1 = 20.;
	double PRODUCT_REMAINDER_2 = 200.;
	double PRODUCT_REMAINDER_3 = 5.;
	double PRODUCT_REMAINDER_4 = 25.;
	double PRODUCT_REMAINDER_5 = 30.5;
	String PRODUCT_MANUFACTURER_1 = "J7Group";
	String PRODUCT_MANUFACTURER_2 = "Carbonated drinks Co.Ltd";
	String PRODUCT_MANUFACTURER_3 = "Camamber Сheese dairy";
	String PRODUCT_MANUFACTURER_4 = "Cheese dairy";
	String PRODUCT_MANUFACTURER_5 = "Tnuva";
	String PRODUCT_COUNTRY_1 = "Russia";
	String PRODUCT_COUNTRY_2 = "Israel";
	String PRODUCT_COUNTRY_3 = "France";
	String PRODUCT_COUNTRY_4 = "Holland";
	String PRODUCT_COUNTRY_5 = "Israel";
	double QUANTITY = 1.;
	int INITIAL_DAYS_MINUS = 4;

}
