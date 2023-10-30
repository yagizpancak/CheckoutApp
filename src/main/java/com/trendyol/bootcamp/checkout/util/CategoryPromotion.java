package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CategoryPromotion implements Promotion{
	@Value("${promotion.categoryPromotionId}")
	private int promotionId;
	@Value("${promotion.categoryPromotionCategoryId}")
	private int promotionCategoryId;
	@Value("${promotion.categoryPromotionDiscountPercentage}")
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
				.anyMatch(item -> item.getCategoryId() == promotionCategoryId);
	}
}
