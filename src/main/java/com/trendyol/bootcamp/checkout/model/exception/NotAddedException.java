package com.trendyol.bootcamp.checkout.model.exception;

public abstract class NotAddedException extends RuntimeException{
	protected NotAddedException(String message) {
		super(message);
	}
}
