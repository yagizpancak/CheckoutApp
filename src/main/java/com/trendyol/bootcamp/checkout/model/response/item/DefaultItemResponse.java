package com.trendyol.bootcamp.checkout.model.response.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({"id"})
@Getter
public class DefaultItemResponse extends ItemResponse {
	@JsonProperty("itemId")
	private int id;
	private final List<VasItemResponse> vasItems;

	public DefaultItemResponse(int id, int categoryId, int sellerId, double price, int quantity) {
		super(categoryId, sellerId, price, quantity);
		this.id = id;
		this.vasItems=new ArrayList<>();
	}


}
