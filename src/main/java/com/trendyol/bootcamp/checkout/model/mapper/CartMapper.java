package com.trendyol.bootcamp.checkout.model.mapper;

import com.trendyol.bootcamp.checkout.model.response.cart.CartResponse;
import com.trendyol.bootcamp.checkout.model.response.item.ItemResponse;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses={ItemMapper.class})
public interface CartMapper {
	CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

	@Mapping(source = "items", target = "items", qualifiedByName = "mapItemsToResponse")
	CartResponse entityToResponse(Cart cart);


	@Named("mapItemsToResponse")
	default List<ItemResponse> mapItemsToResponse(List<Item> sourceItems){
		return sourceItems.stream().map(Item::toResponse).toList();
	}


}
