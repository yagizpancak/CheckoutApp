package com.trendyol.bootcamp.checkout.model.response.item;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonPropertyOrder({"id"})
@Getter
public class VasItemResponse extends ItemResponse {
	@JsonProperty("vasItemId")
	private int id;

	public VasItemResponse(int id, int categoryId, int sellerId, double price, int quantity) {
		super( categoryId, sellerId, price, quantity);
		this.id = id;
	}


}
