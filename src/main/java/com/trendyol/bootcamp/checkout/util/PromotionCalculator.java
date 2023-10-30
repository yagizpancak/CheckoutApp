package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class PromotionCalculator {
	private final List<Promotion> promotionList;

	public PromotionDTO getSuitablePromotion(Cart cart){
		var suitablePromotionList = getPromotionList().stream()
				.filter(promotion -> promotion.checkPromotion(cart)).toList();

		return suitablePromotionList.stream()
				.map(promotion -> promotion.applyPromotion(cart))
				.max(PromotionDTO::compareTo)
				.orElseThrow();

	}
}
