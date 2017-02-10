package com.zup.xyapi.controller.handler;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse<Object> modelValidateResponseError(MethodArgumentNotValidException ex) {

		ApiResponse<Object> apiResponse = new ApiResponse<>();
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

}
