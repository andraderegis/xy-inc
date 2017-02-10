package com.zup.xyapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zup.xyapi.response.ApiResponse;

public abstract class BaseController<T> {
	
	private ApiResponse<T> response;
	
	@SuppressWarnings("rawtypes")
	protected ResponseEntity SuccessResponse(List<T> data){
		
		response = new ApiResponse<>();
		
		response.setCode(HttpStatus.OK.value());
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		response.setData(data);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
