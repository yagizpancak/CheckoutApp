package com.trendyol.bootcamp.checkout.model.response.item;

import lombok.AllArgsConstructor;
import lombok.Getter;



//@JsonPropertyOrder({"id"})
@AllArgsConstructor
@Getter
public abstract class ItemResponse {

	private int categoryId;
	private int sellerId;
	private double price;
	private int quantity;

	public abstract int getId();
}
