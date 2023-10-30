package com.trendyol.bootcamp.checkout.model.entity.item;


import com.trendyol.bootcamp.checkout.model.response.item.VasItemResponse;
import com.trendyol.bootcamp.checkout.model.mapper.ItemMapper;

public class VasItem extends Item {


	public VasItem(int id, int categoryId, int sellerId, double price, int quantity) {
		super(id, categoryId, sellerId, price, quantity);
	}

	@Override
	public VasItemResponse toResponse() {
		return ItemMapper.INSTANCE.entityToResponse(this);
	}
}
