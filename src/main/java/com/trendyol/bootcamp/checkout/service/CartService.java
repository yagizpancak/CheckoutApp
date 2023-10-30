package com.trendyol.bootcamp.checkout.service;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.exception.ItemNotFoundException;
import com.trendyol.bootcamp.checkout.model.request.RemoveItemRequest;
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

	public void removeItem(RemoveItemRequest removeItemRequest){
		var itemToRemove = cartRepository.getItem(removeItemRequest.getItemId())
				.orElseThrow(ItemNotFoundException::new);

		var totalRemovedItemPrice = itemToRemove.getPrice() * itemToRemove.getQuantity() * -1;

		cartRepository.deleteItem(itemToRemove);
		logger.info("Item removed with: {}", removeItemRequest);

		cartRepository.updateTotalPrice(totalRemovedItemPrice);
		checkAndApplyPromotion();
	}

	public void resetCart(){
		logger.info("Cart reset.");
		cartRepository.resetCard();
	}

	public CartResponse displayCart() {
		logger.info("Cart displayed.");
		return cartRepository.getCart().toResponse();
	}

	private void checkAndApplyPromotion(){
		try {
			var promotion = promotionCalculator.getSuitablePromotion(cartRepository.getCart());

			if (promotion.getTotalDiscount() > cartRepository.getTotalPrice()){
				throw new Exception();
			}

			logger.info("Promotion applied: {}", promotion);
			cartRepository.setPromotion(promotion);
		} catch (Exception e){
			logger.info("No promotion applied!");
			cartRepository.setPromotion(PromotionDTO.builder()
					.promotionId(0)
					.totalDiscount(0)
					.build());
		}
	}
}
