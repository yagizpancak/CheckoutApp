package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;

public interface Promotion {
	PromotionDTO applyPromotion(Cart cart);

	boolean checkPromotion(Cart cart);
}
