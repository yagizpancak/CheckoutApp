package com.trendyol.bootcamp.checkout.model.entity.cart;

import com.trendyol.bootcamp.checkout.model.entity.item.Item;
import com.trendyol.bootcamp.checkout.model.mapper.CartMapper;
import com.trendyol.bootcamp.checkout.model.response.cart.CartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
	private List<Item> items = new ArrayList<>();
	private double totalPrice;
	private int appliedPromotionId;
	private double totalDiscount;

	public CartResponse toResponse(){
		return CartMapper.INSTANCE.entityToResponse(this);
	}

}
