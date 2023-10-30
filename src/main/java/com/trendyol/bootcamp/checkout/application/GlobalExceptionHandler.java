package com.trendyol.bootcamp.checkout.application;

import com.trendyol.bootcamp.checkout.model.exception.ItemNotFoundException;
import com.trendyol.bootcamp.checkout.model.exception.LimitExceededException;
import com.trendyol.bootcamp.checkout.model.exception.NotAddedException;
import com.trendyol.bootcamp.checkout.model.exception.VasItemNotValidException;
import com.trendyol.bootcamp.checkout.model.response.ApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<ApplicationResponse<String>> handleNotFoundException(ItemNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				ApplicationResponse.<String>builder()
						.result(false)
						.message(e.getMessage())
						.build()
		);
	}

	@ExceptionHandler(NotAddedException.class)
	public ResponseEntity<ApplicationResponse<String>> handleNotAddedException(NotAddedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				ApplicationResponse.<String>builder()
						.result(false)
						.message(e.getMessage())
						.build()
		);
	}

	@ExceptionHandler(LimitExceededException.class)
	public ResponseEntity<ApplicationResponse<String>> handleLimitExceededException(LimitExceededException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				ApplicationResponse.<String>builder()
						.result(false)
						.message(e.getMessage())
						.build()
		);
	}

	@ExceptionHandler(VasItemNotValidException.class)
	public ResponseEntity<ApplicationResponse<String>> handleVasItemNotValidException(VasItemNotValidException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				ApplicationResponse.<String>builder()
						.result(false)
						.message(e.getMessage())
						.build()
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApplicationResponse<String>> handleGeneralException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				ApplicationResponse.<String>builder()
						.result(false)
						.message("Unknown exception occurred.")
						.build()
		);
	}
}
