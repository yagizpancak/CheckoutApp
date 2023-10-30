package com.trendyol.bootcamp.checkout.model.exception;

public class CartLimitExceededException extends LimitExceededException{
	private static final String MESSAGE = "Cart limit exceeded!";

	public CartLimitExceededException(){
		super(MESSAGE);
	}
}
