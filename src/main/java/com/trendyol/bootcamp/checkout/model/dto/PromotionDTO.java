package com.trendyol.bootcamp.checkout.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PromotionDTO {
	private int promotionId;
	private double totalDiscount;

	public int compareTo(PromotionDTO o) {
		return Double.compare(this.totalDiscount, o.totalDiscount);
	}
}
