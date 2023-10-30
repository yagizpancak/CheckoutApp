package com.trendyol.bootcamp.checkout.service;

import com.trendyol.bootcamp.checkout.model.response.cart.CartResponse;
import com.trendyol.bootcamp.checkout.properties.CartProperties;
import com.trendyol.bootcamp.checkout.repository.CartRepository;
import com.trendyol.bootcamp.checkout.util.PromotionCalculator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;
	private final PromotionCalculator promotionCalculator;
	private final CartProperties cartProperties;
	private final Logger logger = LoggerFactory.getLogger(getClass());



	public CartResponse displayCart() {
		logger.info("Cart displayed.");
		return cartRepository.getCart().toResponse();
	}

}
