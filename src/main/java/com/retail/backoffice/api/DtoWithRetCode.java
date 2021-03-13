package com.retail.backoffice.api;

import com.retail.backoffice.service.interfaces.IRetail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoWithRetCode<T> {
	T dto;
	IRetail.ReturnCodes returnCode;  
}
