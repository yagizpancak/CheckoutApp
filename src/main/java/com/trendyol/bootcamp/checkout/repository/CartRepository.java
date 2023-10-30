package com.trendyol.bootcamp.checkout.repository;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class CartRepository {
	private Cart cart;

	public void resetCard() {
		setCart(new Cart());
	}


	public Optional<Item> getItem(int itemId) {
		return getCart().getItems().stream()
				.filter(item -> item.getId() == itemId)
				.findFirst();
	}

	public void deleteItem(Item item){
		getCart().getItems().remove(item);
	}

	public void setPromotion(PromotionDTO promotion){
		getCart().setAppliedPromotionId(promotion.getPromotionId());
		getCart().setTotalDiscount(promotion.getTotalDiscount());
	}

	public void updateTotalPrice(double totalNewItemPrice) {
		var newTotalPrice = getCart().getTotalPrice() + totalNewItemPrice;
		getCart().setTotalPrice(newTotalPrice);
	}

	public double getTotalPrice() {
		return getCart().getTotalPrice();
	}
}
