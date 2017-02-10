package com.zup.xyapi.controller.handler;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.zup.xyapi.response.ApiResponse;

@ControllerAdvice
public class ValidationHandlerController {

	@Autowired
	private MessageSource messageSource;

	private ApiResponse<Object> apiResponse;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse<Object> modelValidateResponseError(MethodArgumentNotValidException ex) {

		apiResponse = new ApiResponse<>();
		apiResponse.setMessage("");

		List<FieldError> fields = ex.getBindingResult().getFieldErrors();

		if (fields != null) {
			fields.forEach(field -> {
				String message = apiResponse.getMessage() != null
						? apiResponse.getMessage().concat(fieldErrorMessage(field)).concat(".")
						: "".concat(fieldErrorMessage(field)).concat(".");

				apiResponse.setMessage(message);
			});
		}
		
		apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
		apiResponse.setStatus(HttpStatus.BAD_GATEWAY.getReasonPhrase());

		return apiResponse;
	}

	private String fieldErrorMessage(FieldError error) {

		String message = "";

		if (error != null) {
			Locale currentLocale = LocaleContextHolder.getLocale();

			String messageKey = error.getObjectName().concat(".").concat(error.getField()).concat(".")
					.concat(error.getCode().toLowerCase());

			message = messageSource.getMessage(messageKey, null, currentLocale);
		}

		return message;
	}

	@ExceptionHandler(value = { HttpMediaTypeException.class })
	@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ResponseBody
	public ApiResponse<Object> unsuportedMediaResponseError(HttpMediaTypeException e) {

		return responseError(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
	}

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ApiResponse<Object> genericResponseError(Exception e) {
		
		return responseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

	}

	private ApiResponse<Object> responseError(String message, int statusCode, String statusMessage) {

		apiResponse = new ApiResponse<>();
		apiResponse.setMessage(message);

		apiResponse.setCode(statusCode);
		apiResponse.setStatus(statusMessage);

		return apiResponse;

	}

}
