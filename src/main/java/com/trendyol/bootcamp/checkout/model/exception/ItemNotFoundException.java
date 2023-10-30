package com.trendyol.bootcamp.checkout.model.exception;

public class ItemNotFoundException extends RuntimeException{
	private static final String MESSAGE = "Item not found by item id!";

	public ItemNotFoundException(){
		super(MESSAGE);
	}
}
