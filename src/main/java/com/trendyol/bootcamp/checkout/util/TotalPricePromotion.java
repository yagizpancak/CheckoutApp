package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TotalPricePromotion implements Promotion{
	@Value("${promotion.totalPricePromotionId}")
	private int promotionId;
	@Override
	public PromotionDTO applyPromotion(Cart cart) {
		var totalPrice = cart.getTotalPrice();
		double totalDiscount;

		if (totalPrice < 5000) totalDiscount = 250;
		else if (totalPrice < 10000) totalDiscount = 500;
		else if (totalPrice < 50000) totalDiscount = 1000;
		else totalDiscount = 2000;

		return PromotionDTO.builder()
				.promotionId(promotionId)
				.totalDiscount(totalDiscount)
				.build();
	}

	@Override
	public boolean checkPromotion(Cart cart) {
		return true;
	}
}
