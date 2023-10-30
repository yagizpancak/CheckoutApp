package com.trendyol.bootcamp.checkout.model.exception;

public class ItemQuantityExceedException extends LimitExceededException{
	private static final String MESSAGE = "Quantity has exceed the maximum limit %s !";

	public ItemQuantityExceedException(int maxQuantity){
		super(String.format(MESSAGE, maxQuantity));
	}
}
