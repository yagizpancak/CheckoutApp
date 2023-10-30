package com.trendyol.bootcamp.checkout.model.response.cart;

import com.trendyol.bootcamp.checkout.model.response.item.ItemResponse;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class CartResponse {
	private List<ItemResponse> items;
	private double totalPrice;
	private int appliedPromotionId;
	private double totalDiscount;

}
