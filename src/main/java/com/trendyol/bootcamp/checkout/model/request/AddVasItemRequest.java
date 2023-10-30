package com.trendyol.bootcamp.checkout.model.request;

import com.trendyol.bootcamp.checkout.model.entity.item.VasItem;
import com.trendyol.bootcamp.checkout.model.mapper.ItemMapper;
import lombok.Data;

@Data
public class AddVasItemRequest {
	private int itemId;
	private int vasItemId;
	private int categoryId;
	private int sellerId;
	private double price;
	private int quantity;

	public VasItem toEntity(){
		return ItemMapper.INSTANCE.requestToVasItem(this);
	}
}
