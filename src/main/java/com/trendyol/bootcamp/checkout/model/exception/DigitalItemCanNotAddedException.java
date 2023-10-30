package com.trendyol.bootcamp.checkout.model.exception;

public class DigitalItemCanNotAddedException extends NotAddedException{
	private static final String MESSAGE = "Cart contains Default Item, Digital item can not be added!";

	public DigitalItemCanNotAddedException(){
		super(MESSAGE);
	}
}
