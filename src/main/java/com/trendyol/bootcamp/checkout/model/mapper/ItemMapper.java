package com.trendyol.bootcamp.checkout.model.mapper;

import com.trendyol.bootcamp.checkout.model.request.AddItemRequest;
import com.trendyol.bootcamp.checkout.model.request.AddVasItemRequest;
import com.trendyol.bootcamp.checkout.model.response.item.DefaultItemResponse;
import com.trendyol.bootcamp.checkout.model.response.item.DigitalItemResponse;
import com.trendyol.bootcamp.checkout.model.response.item.VasItemResponse;
import com.trendyol.bootcamp.checkout.model.entity.item.DefaultItem;
import com.trendyol.bootcamp.checkout.model.entity.item.DigitalItem;
import com.trendyol.bootcamp.checkout.model.entity.item.VasItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

	ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

	DefaultItemResponse entityToResponse(DefaultItem defaultItem);
	DigitalItemResponse entityToResponse(DigitalItem digitalItem);
	VasItemResponse entityToResponse(VasItem vasItem);


	@Mapping(target = "id", source = "addItemRequest.itemId")
	DefaultItem requestToDefaultItem(AddItemRequest addItemRequest);
	@Mapping(target = "id", source = "addItemRequest.itemId")
	DigitalItem requestToDigitalItem(AddItemRequest addItemRequest);
	@Mapping(target = "id", source = "addVasItemRequest.vasItemId")
	VasItem requestToVasItem(AddVasItemRequest addVasItemRequest);
}
