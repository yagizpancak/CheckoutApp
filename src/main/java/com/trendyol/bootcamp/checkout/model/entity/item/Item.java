package com.trendyol.bootcamp.checkout.model.entity.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trendyol.bootcamp.checkout.model.response.item.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@JsonPropertyOrder({"id"})
@AllArgsConstructor
@Getter
public abstract class Item {

	@JsonProperty("itemId")
	private int id;
	private int categoryId;
	private int sellerId;
	private double price;
	@Setter
	private int quantity;

	public abstract ItemResponse toResponse();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Item that = (Item) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
