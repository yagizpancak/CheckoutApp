package com.trendyol.bootcamp.checkout.service;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.exception.*;
import com.trendyol.bootcamp.checkout.model.request.AddItemRequest;
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

	public void addItem(AddItemRequest addItemRequest){
		if (hasCartLimitExceeded(addItemRequest.getQuantity(), addItemRequest.getPrice())){
			throw new CartLimitExceededException();
		}

		if (addItemRequest.getCategoryId() == cartProperties.getDigitalItemCategoryId()) {
			addDigitalItem(addItemRequest);
		}else{
			addDefaultItem(addItemRequest);
		}
		logger.info("Item added with: {}", addItemRequest);

		var totalNewItemPrice = addItemRequest.getQuantity() * addItemRequest.getPrice();
		cartRepository.updateTotalPrice(totalNewItemPrice);
		checkAndApplyPromotion();
	}

	private void addDefaultItem(AddItemRequest addItemRequest) {
		if (cartRepository.hasCartDigitalItem()){
			throw new DefaultItemCanNotAddedException();
		}

		if (hasExceedMaxItemQuantity(addItemRequest, cartProperties.getMaxItemQuantity())){
			throw new ItemQuantityExceedException(cartProperties.getMaxItemQuantity());
		}

		cartRepository.saveItem(addItemRequest.toDefaultItemEntity());
	}

	private void addDigitalItem(AddItemRequest addItemRequest) {
		if (cartRepository.hasCartDefaultItem()){
			throw new DigitalItemCanNotAddedException();
		}

		if (hasExceedMaxItemQuantity(addItemRequest, cartProperties.getMaxDigitalItemQuantity())) {
			throw new ItemQuantityExceedException(cartProperties.getMaxDigitalItemQuantity());
		}
		cartRepository.saveItem(addItemRequest.toDigitalItemEntity());
	}

	private boolean hasCartLimitExceeded(int quantity, double price) {
		if (cartRepository.getTotalItemNumber() + quantity > cartProperties.getMaxItem()){
			return true;
		}

		if (cartRepository.getTotalUniqueItemNumber() >= cartProperties.getMaxUniqueItem() ){
			return true;
		}

		var totalNewItemPrice = quantity * price;
		return cartRepository.getCart().getTotalPrice() + totalNewItemPrice > cartProperties.getMaxTotalPrice();
	}

	private boolean hasExceedMaxItemQuantity(AddItemRequest addItemRequest, int maxQuantity) {
		var itemInCart = cartRepository.getItem(addItemRequest.getItemId());
		int cartQuantity;
		if (itemInCart.isEmpty()) {
			return false;
		} else {
			cartQuantity = itemInCart.get().getQuantity();
		}

		var quantitySum = addItemRequest.getQuantity() + cartQuantity;
		return quantitySum > maxQuantity;
	}

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
