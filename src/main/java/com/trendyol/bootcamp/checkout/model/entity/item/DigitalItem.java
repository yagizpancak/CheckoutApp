package com.trendyol.bootcamp.checkout.model.entity.item;

import com.trendyol.bootcamp.checkout.model.response.item.DigitalItemResponse;

import com.trendyol.bootcamp.checkout.model.mapper.ItemMapper;

public class DigitalItem extends Item {

	public DigitalItem(int id, int categoryId, int sellerId, double price, int quantity) {
		super(id, categoryId, sellerId, price, quantity);
	}

	@Override
	public DigitalItemResponse toResponse() {
		return ItemMapper.INSTANCE.entityToResponse(this);
	}
}
