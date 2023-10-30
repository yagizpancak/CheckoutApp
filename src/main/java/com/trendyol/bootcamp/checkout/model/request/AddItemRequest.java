package com.trendyol.bootcamp.checkout.model.request;

import com.trendyol.bootcamp.checkout.model.entity.item.DigitalItem;
import com.trendyol.bootcamp.checkout.model.entity.item.Item;
import com.trendyol.bootcamp.checkout.model.mapper.ItemMapper;
import lombok.Data;

@Data
public class AddItemRequest {
	private int itemId;
	private int categoryId;
	private int sellerId;
	private double price;
	private int quantity;

	public DigitalItem toDigitalItemEntity() {
		return ItemMapper.INSTANCE.requestToDigitalItem(this);
	}

	public Item toDefaultItemEntity(){
		return ItemMapper.INSTANCE.requestToDefaultItem(this);
	}
}
