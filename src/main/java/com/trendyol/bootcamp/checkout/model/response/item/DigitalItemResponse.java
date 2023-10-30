package com.trendyol.bootcamp.checkout.model.response.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonPropertyOrder({"id"})
@Getter
public class DigitalItemResponse extends ItemResponse {
	@JsonProperty("itemId")
	private int id;

	public DigitalItemResponse(int id, int categoryId, int sellerId, double price, int quantity) {
		super(categoryId, sellerId, price, quantity);
		this.id=id;
	}

}
