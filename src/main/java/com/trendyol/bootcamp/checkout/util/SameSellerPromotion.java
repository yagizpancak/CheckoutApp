package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SameSellerPromotion implements Promotion{
	@Value("${promotion.sameSellerPromotionId}")
	private int promotionId;
	@Value("${promotion.sameSellerPromotionDiscountPercentage}")
	private double discountPercentage;
	@Override
	public PromotionDTO applyPromotion(Cart cart) {
		return PromotionDTO.builder()
				.promotionId(promotionId)
				.totalDiscount(cart.getTotalPrice() * discountPercentage)
				.build();
	}

	@Override
	public boolean checkPromotion(Cart cart) {
		return cart.getItems().stream()
				.map(Item::getSellerId)
				.distinct()
				.count() == 1;
	}
}
