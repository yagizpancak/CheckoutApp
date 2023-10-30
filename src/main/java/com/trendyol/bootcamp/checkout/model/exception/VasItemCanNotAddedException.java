package com.trendyol.bootcamp.checkout.model.exception;

public class VasItemCanNotAddedException extends NotAddedException{
	private static final String MESSAGE = "Vas Item can only be added to Default Item!";

	public VasItemCanNotAddedException(){
		super(MESSAGE);
	}
}
