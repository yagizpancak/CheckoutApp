package com.trendyol.bootcamp.checkout.model.exception;

public class VasItemNotValidException extends RuntimeException{
	private static final String MESSAGE = "Vas Item not valid!";

	public VasItemNotValidException(){
		super(MESSAGE);
	}
}
