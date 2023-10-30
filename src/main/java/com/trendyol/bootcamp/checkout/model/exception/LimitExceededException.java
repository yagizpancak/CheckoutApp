package com.trendyol.bootcamp.checkout.model.exception;

public abstract class LimitExceededException extends RuntimeException{

	protected LimitExceededException(String message) {
		super(message);
	}
}
