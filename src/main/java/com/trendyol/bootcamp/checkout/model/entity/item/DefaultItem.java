package com.trendyol.bootcamp.checkout.model.entity.item;

import com.trendyol.bootcamp.checkout.model.response.item.DefaultItemResponse;
import com.trendyol.bootcamp.checkout.model.mapper.ItemMapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class DefaultItem extends Item {
	private final List<VasItem> vasItems;

	public DefaultItem(int id, int categoryId, int sellerId, double price, int quantity) {
		super(id, categoryId, sellerId, price, quantity);
		this.vasItems=new ArrayList<>();
	}

	public void addVasItem(VasItem vasItem){
		getVasItems().add(vasItem);
	}

	@Override
	public DefaultItemResponse toResponse() {
		return ItemMapper.INSTANCE.entityToResponse(this);
	}

	@Override
	public int getQuantity(){
		return super.getQuantity() + getVasItems().stream()
				.mapToInt(Item::getQuantity)
				.sum();
	}
}
