package com.retail.backoffice.api;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDto {
	@Builder.Default
	private String timestamp=LocalDateTime.now().toString();
	private int status;
	@Builder.Default
	private String error="";
	@Builder.Default
	private String message="";
	@Builder.Default
	private String path="";
}
