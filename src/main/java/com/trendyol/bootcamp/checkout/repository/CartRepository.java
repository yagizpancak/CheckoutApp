package com.trendyol.bootcamp.checkout.repository;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.DefaultItem;
import com.trendyol.bootcamp.checkout.model.entity.item.DigitalItem;
import com.trendyol.bootcamp.checkout.model.entity.item.Item;
import lombok.*;
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

	public void saveItem(Item item){
		if (isNewItemIdExist(item.getId())){
			updateItem(item);
			return;
		}

		insertItem(item);
	}

	private boolean isNewItemIdExist(int itemId){
		return getCart().getItems().stream().anyMatch(item -> item.getId()==itemId);
	}

	private void updateItem(Item item){
		var itemIndex = getCart().getItems().indexOf(item);
		var toUpdatedItem = getCart().getItems().get(itemIndex);
		toUpdatedItem.setQuantity(toUpdatedItem.getQuantity() + item.getQuantity());
	}

	private void insertItem(Item item){
		getCart().getItems().add(item);
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

	public boolean hasCartDigitalItem() {
		return getCart().getItems().stream().anyMatch(DigitalItem.class::isInstance);
	}

	public boolean hasCartDefaultItem() {
		return getCart().getItems().stream().anyMatch(DefaultItem.class::isInstance);
	}

	public int getTotalUniqueItemNumber() {
		return getCart().getItems().size();
	}

	public int getTotalItemNumber() {
		return getCart().getItems().stream()
				.mapToInt(Item::getQuantity)
				.sum();
	}

	public void updateTotalPrice(double totalNewItemPrice) {
		var newTotalPrice = getCart().getTotalPrice() + totalNewItemPrice;
		getCart().setTotalPrice(newTotalPrice);
	}

	public double getTotalPrice() {
		return getCart().getTotalPrice();
	}
}
