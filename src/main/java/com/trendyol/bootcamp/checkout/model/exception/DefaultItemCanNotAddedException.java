package com.trendyol.bootcamp.checkout.model.exception;

public class DefaultItemCanNotAddedException extends NotAddedException{
	private static final String MESSAGE = "Cart contains Digital Item, Default item can not be added!";

	public DefaultItemCanNotAddedException(){
		super(MESSAGE);
	}
}
