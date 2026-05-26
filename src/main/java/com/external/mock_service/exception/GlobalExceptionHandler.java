package com.external.mock_service.exception;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, Object> handleNotFound(ResourceNotFoundException exception) {
		return Map.of(
				"timestamp", Instant.now().toString(),
				"status", HttpStatus.NOT_FOUND.value(),
				"error", "Not Found",
				"message", exception.getMessage()
		);
	}
}
